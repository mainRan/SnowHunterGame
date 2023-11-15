package me.terramain.guiapi.guiFunctions;

public enum ClickType {
    left_click,
    left_shift_click,
    left_outside_gui,
    left_double_click,
    right_click,
    right_shift_click,
    right_outside_gui,
    middle_click,
    num,
    drop,
    whole_slot_drop,
    unknown;

    public static ClickType clickType(org.bukkit.event.inventory.ClickType clickType){
        if (clickType == org.bukkit.event.inventory.ClickType.LEFT) return ClickType.left_click;
        if (clickType == org.bukkit.event.inventory.ClickType.SHIFT_LEFT) return ClickType.left_shift_click;
        if (clickType == org.bukkit.event.inventory.ClickType.WINDOW_BORDER_LEFT) return ClickType.left_outside_gui;
        if (clickType == org.bukkit.event.inventory.ClickType.DOUBLE_CLICK) return ClickType.left_double_click;
        if (clickType == org.bukkit.event.inventory.ClickType.RIGHT) return ClickType.right_click;
        if (clickType == org.bukkit.event.inventory.ClickType.SHIFT_RIGHT) return ClickType.right_shift_click;
        if (clickType == org.bukkit.event.inventory.ClickType.WINDOW_BORDER_RIGHT) return ClickType.right_outside_gui;
        if (clickType == org.bukkit.event.inventory.ClickType.MIDDLE) return ClickType.middle_click;
        if (clickType == org.bukkit.event.inventory.ClickType.NUMBER_KEY) return ClickType.num;
        if (clickType == org.bukkit.event.inventory.ClickType.DROP) return ClickType.drop;
        if (clickType == org.bukkit.event.inventory.ClickType.CONTROL_DROP) return ClickType.whole_slot_drop;
        else return ClickType.unknown;
    }


}
