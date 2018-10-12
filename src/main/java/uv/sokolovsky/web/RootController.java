package uv.sokolovsky.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import uv.sokolovsky.AuthorizedUser;
import uv.sokolovsky.model.Restaurant;
import uv.sokolovsky.service.DishService;
import uv.sokolovsky.service.RestaurantService;
import uv.sokolovsky.to.UserTo;
import uv.sokolovsky.util.UserUtil;
import uv.sokolovsky.web.user.AbstractUserController;
import uv.sokolovsky.web.util.PasswordUtil;

import javax.validation.Valid;

import java.util.List;

import static uv.sokolovsky.util.DishUtil.getDishesDescriptions;
import static uv.sokolovsky.web.ExceptionInfoHandler.EXCEPTION_DUPLICATE_EMAIL;
import static uv.sokolovsky.web.ExceptionInfoHandler.EXCEPTION_WRONG_PASSWORD;

@Controller
public class RootController extends AbstractUserController {

    @Autowired
    private PasswordUtil passwordUtil;

    @Autowired
    private RestaurantService restaurantService;

    @Autowired
    private DishService dishService;

    @GetMapping("/")
    public String root() {
        return "redirect:votes";
    }

    @GetMapping(value = "/login")
    public String login() {
        return "login";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public String users() {
        return "users";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/restaurants")
    public String restaurants () {
        return "restaurants";
    }

    @GetMapping("/dishes")
    public String dishes (@RequestParam(value="restaurantId", required = false) Integer restaurantId, ModelMap model) {
        Restaurant restaurant = restaurantService.get(restaurantId);
        List<String> dishesDescriptions = getDishesDescriptions(dishService.getAll(restaurant));
        model.addAttribute("dishesDescriptions", dishesDescriptions);
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("restaurantId", restaurantId);
        return "dishes";
    }

    @GetMapping("/votes")
    public String votes () {
        return "votes";
    }

    @GetMapping("/profile")
    public String profile(ModelMap model, @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        model.addAttribute("userTo", authorizedUser.getUserTo());
        return "profile";
    }

    @PostMapping("/profile")
    public String updateProfile(@Valid UserTo userTo, BindingResult result, SessionStatus status, @AuthenticationPrincipal AuthorizedUser authorizedUser) {
        if (result.hasErrors()) {
            return "profile";
        }
        try {
            if (passwordUtil.validatePassword(userTo.getPassword())){
                super.update(userTo, authorizedUser.getId());
                authorizedUser.update(userTo);
                status.setComplete();
            } else {
                result.rejectValue("password", EXCEPTION_WRONG_PASSWORD);
                return "profile";
            }
            return "redirect:votes";
        } catch (DataIntegrityViolationException ex) {
            result.rejectValue("email", EXCEPTION_DUPLICATE_EMAIL);
            return "profile";
        }
    }

    @PostMapping("/profile/password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePassword(@RequestParam(value="oldPassword") String oldPassword,
                               @RequestParam(value="newPassword") String newPassword,
                               @AuthenticationPrincipal AuthorizedUser authorizedUser){
        if (passwordUtil.validatePassword(oldPassword, newPassword)) {
            UserTo userTo = authorizedUser.getUserTo();
            userTo.setPassword(newPassword);
            super.update(userTo, authorizedUser.getId());
        }
    }

    @GetMapping("/register")
    public String register(ModelMap model) {
        model.addAttribute("userTo", new UserTo());
        model.addAttribute("register", true);
        return "profile";
    }

    @PostMapping("/register")
    public String saveRegister(@Valid UserTo userTo, BindingResult result, SessionStatus status, ModelMap model) {
        if (result.hasErrors()) {
            model.addAttribute("register", true);
            return "profile";
        }
        try {
            super.create(UserUtil.createNewFromTo(userTo));
            status.setComplete();
            return "redirect:login?message=app.registered&username=" + userTo.getEmail();
        } catch (DataIntegrityViolationException ex) {
            result.rejectValue("email", EXCEPTION_DUPLICATE_EMAIL);
            model.addAttribute("register", true);
            return "profile";
        }
    }
}
