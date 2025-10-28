package io.github.myueqf.QuickSwap.client;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.screen.slot.SlotActionType;
import org.lwjgl.glfw.GLFW;
// import net.minecraft.text.Text;

/**
 * 9-35: 主背包(main inventory)
 * 其中 9-17: 第一行, 18-26: 第二行, 27-35: 第三行
 * 36-44: 物品栏(hotbar)
 */
public class QAQ {
    private static KeyBinding swapKeybind;
    private static int currentRow = 2;
    private static final int QwQ = 0; // 0关闭，1启用，可以依次交换整个背包～
    private static int OAO = 0;

    public static void XwX() {
        swapKeybind = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.SwapInventory.QAQ",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_TAB,
                KeyBinding.Category.INVENTORY
        ));
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (swapKeybind.wasPressed()) {
                swapInventoryRows();
            }
        });
    }
    private static void swapInventoryRows() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null || client.interactionManager == null) {
            return;
        }
        // 确保不在任何GUI中
        if (client.currentScreen != null) {
            return;
        }

        int backpackRowStart = 9 + (currentRow * 9);
        for (int i = 0; i < 9; i++) {
            int hotbarSlot = 36 + i;  // 物品栏：36-44
            int backpackSlot = backpackRowStart + i;
            swapSlots(client, hotbarSlot, backpackSlot);
        }

        if (QwQ == 1){
            if (OAO == 1) {
                // 更新到下一行（循环：0->1->2->0）
                currentRow = (currentRow + 1) % 3;
            }
            OAO = (OAO + 1) % 2;
        } else {
            currentRow = 2;
        }
    }

    private static void swapSlots(MinecraftClient client, int slot1, int slot2) {
        // 左键点击第一个槽位（拾取物品）
        client.interactionManager.clickSlot(
                client.player.playerScreenHandler.syncId,
                slot1,
                0,
                SlotActionType.PICKUP,
                client.player
        );

        // 左键点击第二个槽位（放下物品并拾取那里的物品）
        client.interactionManager.clickSlot(
                client.player.playerScreenHandler.syncId,
                slot2,
                0,
                SlotActionType.PICKUP,
                client.player
        );

        // 左键点击第一个槽位（放下物品）
        client.interactionManager.clickSlot(
                client.player.playerScreenHandler.syncId,
                slot1,
                0,
                SlotActionType.PICKUP,
                client.player
        );
    }

}
