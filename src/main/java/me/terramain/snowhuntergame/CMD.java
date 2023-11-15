package me.terramain.snowhuntergame;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CMD implements TabExecutor {

    public CMD(){
        Main.getPlugin().getCommand("snowhuntergame").setExecutor(this);
        Main.getPlugin().getCommand("snowhuntergame").setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length==0){
            commandSender.sendMessage("вы нечего не указали.");
            return true;
        }
        Player player = (Player) commandSender;
        if (args[0].equals("join")){
            //указать имя арены
            if (args.length==1){
                commandSender.sendMessage("укажите имя арены, к которой вы хотите подключится");
                return true;
            }
            //арена
            Arena arena = ArenaManager.getArena(args[1]);
            //арена существует?
            if (arena==null){
                commandSender.sendMessage("Нам кажется что такой арены не существует...");
                return true;
            }
            //принудительность
            boolean forced = false;
            if (args.length==3) forced = Boolean.parseBoolean(args[2]);

            //удалось ли подключиться к арене.
            boolean result = arena.joinGame(player,forced);
            if (!result) commandSender.sendMessage("к сожалению вы не можете подключиться к арене! Наверное она переполнена.");
            return true;
        }
        if (args[0].equals("admin")){
            if (args.length==1){
                commandSender.sendMessage("недостаточно аргументов.");
                return true;
            }
            if (args[1].equals("arena")){
                if (args.length==2){
                    commandSender.sendMessage("недостаточно аргументов.");
                    return true;
                }
                if (args[2].equals("create")){
                    if (args.length==3){
                        commandSender.sendMessage("укажите имя арены.");
                        return true;
                    }
                    if (args.length==4){
                        commandSender.sendMessage("укажите имя мира, в котором будет арена.");
                        return true;
                    }
                    Arena arena = ArenaManager.createArena(args[3],args[4]);
                    if (arena==null) commandSender.sendMessage("арена с таким именем уже существует.");
                    else commandSender.sendMessage("арена создана!");
                    return true;
                }
                if (args[2].equals("delete")){
                    if (args.length==3){
                        commandSender.sendMessage("укажите имя арены.");
                        return true;
                    }
                    boolean result = ArenaManager.deleteArena(args[3]);
                    if (result) commandSender.sendMessage("арена успешно удалена!");
                    else commandSender.sendMessage("арена с таким именем не существует.");
                    return true;
                }
                if (args[2].equals("list")){
                    commandSender.sendMessage("арены:");
                    for (Arena arena : ArenaManager.arenaList) {
                        commandSender.sendMessage("     "+arena.name);
                    }
                    return true;
                }
                if (args[2].equals("setting")){
                    if (args.length==3){
                        commandSender.sendMessage("укажите имя арены.");
                        return true;
                    }
                    if (args.length==4){
                        commandSender.sendMessage("укажите настройку, который вы хотите поменять. (список настроек: /snowhuntergame admin arena settinglist)");
                        return true;
                    }
                    Arena arena = ArenaManager.getArena(args[3]);
                    if (arena==null){
                        commandSender.sendMessage("Нам кажется что такой арены не существует...");
                        return true;
                    }
                    Boolean settingHaveParams = arena.settingHaveParams(args[4]);
                    if (settingHaveParams==null){
                        commandSender.sendMessage("такой настройки арены не существует.");
                        return true;
                    }
                    if (!settingHaveParams) arena.setting(args[4],player.getName());
                    if (settingHaveParams) {
                        if (args.length==5){
                            commandSender.sendMessage("эта настройка требует дополнительный параметр.");
                            return true;
                        }
                        else {
                            arena.setting(args[4], args[5]);
                        }
                    }
                    commandSender.sendMessage("перенастроено.");
                    return true;
                }
                if (args[2].equals("settinglist")){
                    commandSender.sendMessage("lobbyspawn - устоновить спавн при подключение к лобби арены (от места игрока)");
                    commandSender.sendMessage("playersspawn - устоновить спавн игроков во время начала игры (от места игрока)");
                    commandSender.sendMessage("huntersspawn - устоновить спавн снеговиков во время начала игры(от места игрока)");
                    commandSender.sendMessage("playersnumber <number> - устоновить количество игроков для начала игры");
                    commandSender.sendMessage("huntersnumber <number> - устоновить количество снеговиков для начала игры");
                    commandSender.sendMessage("gameduration <seconds> - устоновить длительность игры в секундах");
                    commandSender.sendMessage("durationforhide <seconds> - устоновить длительность задержки в начале игры, когда игроки могут спрятаться.");
                    return true;
                }
                commandSender.sendMessage("что-то не так...");
                return true;
            }
            commandSender.sendMessage("что-то не так...");
            return true;
        }
        if (args[0].equals("help")){
            if (args.length==1) {
                commandSender.sendMessage("/snowhuntergame join - присоединиться к арене (/snowhuntergame help join)");
                commandSender.sendMessage("/snowhuntergame admin arena create - создать арену (/snowhuntergame help admin arena create)");
                commandSender.sendMessage("/snowhuntergame admin arena delete - удалить арену (/snowhuntergame help admin arena delete)");
                commandSender.sendMessage("/snowhuntergame admin arena setting - настроить арену (/snowhuntergame help admin arena setting)");
                commandSender.sendMessage("/snowhuntergame admin arena settinglist - список настроек");
                return true;
            }
            if (args.length==2){
                if (args[1].equals("join")){
                    commandSender.sendMessage("/snowhuntergame join <arena_name> ?<forced> - присоединиться к арене");
                    commandSender.sendMessage("     <arena_name> - имя арены");
                    commandSender.sendMessage("     <forced> - принудительно? true/false (по умолчанию: false)");
                    return true;
                }
            }
            if (args.length==4){
                if (args[1].equals("admin")) {
                    if (args[2].equals("arena")) {
                        if (args[3].equals("create")) {
                            commandSender.sendMessage("/snowhuntergame admin arena create <arena_name> <arena_world> - создать арену");
                            commandSender.sendMessage("     <arena_name> - имя арены");
                            commandSender.sendMessage("     <arena_world> - мир, в котором находится арена");
                            return true;
                        }
                        if (args[3].equals("delete")) {
                            commandSender.sendMessage("/snowhuntergame admin arena delete <arena_name> - удалить арену");
                            commandSender.sendMessage("     <arena_name> - имя арены");
                            return true;
                        }
                        if (args[3].equals("setting")) {
                            commandSender.sendMessage("/snowhuntergame admin arena setting <arena_name> <setting_name> ?<value> - настроить арену");
                            commandSender.sendMessage("     <arena_name> - имя арены");
                            commandSender.sendMessage("     <setting_name> - имя настройки");
                            commandSender.sendMessage("     <value> - значение (если нужно)");
                            commandSender.sendMessage("     список настроек можно узнать через /snowhuntergame admin arena settinglist");
                            return true;
                        }
                        commandSender.sendMessage("Упс! Этой команды нет, или для неё не добавлены подсказки...");
                        return true;
                    }
                    commandSender.sendMessage("Упс! Этой команды нет, или для неё не добавлены подсказки...");
                    return true;
                }
                commandSender.sendMessage("Упс! Этой команды нет, или для неё не добавлены подсказки...");
                return true;
            }
            commandSender.sendMessage("Упс! Этой команды нет, или для неё не добавлены подсказки...");
            return true;
        }
        return true;
    }

    /*
    /showhuntergame join <arena_name> ?<forced>

    для адм:
    /showhuntergame admin arena create <arenaName> <arenaWorld>
    /showhuntergame admin arena delete <arenaName>

    /showhuntergame admin arena setting <arenaname> lobbyspawn
    /showhuntergame admin arena setting <arenaname> playersspawn
    /showhuntergame admin arena setting <arenaname> huntersspawn
    /showhuntergame admin arena setting <arenaname> playersnumber <number>
    /showhuntergame admin arena setting <arenaname> huntersnumber <number>
    /showhuntergame admin arena setting <arenaname> gameduration <seconds>
    /showhuntergame admin arena setting <arenaname> durationforhide <seconds>

    */

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        return new ArrayList<>();
    }
}
