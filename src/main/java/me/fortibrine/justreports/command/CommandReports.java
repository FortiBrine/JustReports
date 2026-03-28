package me.fortibrine.justreports.command;

import dev.rollczi.litecommands.annotations.command.Command;
import dev.rollczi.litecommands.annotations.context.Context;
import dev.rollczi.litecommands.annotations.execute.Execute;
import dev.rollczi.litecommands.annotations.permission.Permission;
import lombok.RequiredArgsConstructor;
import me.fortibrine.justreports.gui.MenuFactory;
import org.bukkit.entity.Player;

@RequiredArgsConstructor
@Command(name = "reports")
@Permission("justreports.reports")
public class CommandReports {

    private final MenuFactory menuFactory;

    @Execute
    public void execute(@Context Player player) {
        menuFactory.openReportListMenu(player);
    }

}
