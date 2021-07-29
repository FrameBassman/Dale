package tech.romashov.dale.application.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bonus")
public class BonusController {
    @Autowired
    private BonusRepository bonuses;

    @GetMapping("all")
    public Iterable<BonusEntity> GetAll() {
        return bonuses.findAll();
    }
}