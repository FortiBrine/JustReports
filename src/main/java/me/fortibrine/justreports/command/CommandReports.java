package me.fortibrine.justreports.command;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import me.fortibrine.justreports.gui.MenuPage;
import org.bukkit.entity.Player;
import ru.boomearo.menuinv.api.Menu;

@RequiredArgsConstructor
@Command(name = "reports")
@Permission("justreports.reports")
public class CommandReports {

    @Execute
    public void execute(@Context Player player) {
        Menu.open(MenuPage.REPORT_LIST, player);
    }

}
