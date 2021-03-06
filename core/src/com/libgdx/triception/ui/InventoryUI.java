package com.libgdx.triception.ui;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.utils.Array;
import com.libgdx.triception.Utility;

public class InventoryUI extends Window {

    private int _numSlots = 50;
    private int _lengthSlotRow = 10;
    private Table _inventorySlotTable;
    private Table _playerSlotsTable;
    private Table _equipSlots;
    private DragAndDrop _dragAndDrop;
    private Array<Actor> _inventoryActors;

    private final int _slotWidth = 52;
    private final int _slotHeight = 52;

    private InventorySlotTooltip _inventorySlotTooltip;

    public InventoryUI() {
        super("Inventory", Utility.STATUSUI_SKIN, "solidbackground");

        _dragAndDrop = new DragAndDrop();
        _inventoryActors = new Array<Actor>();

        //create
        _inventorySlotTable = new Table();
        _inventorySlotTable.setName("Inventory_Slot_Table");

        _playerSlotsTable = new Table();
        _equipSlots = new Table();
        _equipSlots.setName("Equipment_Slot_Table");

        _equipSlots.defaults().space(10);
        _inventorySlotTooltip = new InventorySlotTooltip(Utility.STATUSUI_SKIN);

        InventorySlot headSlot = new InventorySlot(
                InventoryItem.ItemUseType.ARMOR_HELMET.getValue(),
                new Image(Utility.ITEMS_TEXTUREATLAS.findRegion("inv_helmet")));

        InventorySlot leftArmSlot = new InventorySlot(
                InventoryItem.ItemUseType.WEAPON_ONEHAND.getValue() |
                        InventoryItem.ItemUseType.WEAPON_TWOHAND.getValue() |
                        InventoryItem.ItemUseType.ARMOR_SHIELD.getValue() |
                        InventoryItem.ItemUseType.WAND_ONEHAND.getValue() |
                        InventoryItem.ItemUseType.WAND_TWOHAND.getValue(),
                new Image(Utility.ITEMS_TEXTUREATLAS.findRegion("inv_weapon"))
        );

        InventorySlot rightArmSlot = new InventorySlot(
                InventoryItem.ItemUseType.WEAPON_ONEHAND.getValue() |
                        InventoryItem.ItemUseType.WEAPON_TWOHAND.getValue() |
                        InventoryItem.ItemUseType.ARMOR_SHIELD.getValue() |
                        InventoryItem.ItemUseType.WAND_ONEHAND.getValue() |
                        InventoryItem.ItemUseType.WAND_TWOHAND.getValue(),
                new Image(Utility.ITEMS_TEXTUREATLAS.findRegion("inv_shield"))
        );

        InventorySlot chestSlot = new InventorySlot(
                InventoryItem.ItemUseType.ARMOR_CHEST.getValue(),
                new Image(Utility.ITEMS_TEXTUREATLAS.findRegion("inv_chest")));

        InventorySlot legsSlot = new InventorySlot(
                InventoryItem.ItemUseType.ARMOR_FEET.getValue(),
                new Image(Utility.ITEMS_TEXTUREATLAS.findRegion("inv_boot")));

        headSlot.addListener(new InventorySlotTooltipListener(_inventorySlotTooltip));
        leftArmSlot.addListener(new InventorySlotTooltipListener(_inventorySlotTooltip));
        rightArmSlot.addListener(new InventorySlotTooltipListener(_inventorySlotTooltip));
        chestSlot.addListener(new InventorySlotTooltipListener(_inventorySlotTooltip));
        legsSlot.addListener(new InventorySlotTooltipListener(_inventorySlotTooltip));

        _dragAndDrop.addTarget(new InventorySlotTarget(headSlot));
        _dragAndDrop.addTarget(new InventorySlotTarget(leftArmSlot));
        _dragAndDrop.addTarget(new InventorySlotTarget(chestSlot));
        _dragAndDrop.addTarget(new InventorySlotTarget(rightArmSlot));
        _dragAndDrop.addTarget(new InventorySlotTarget(legsSlot));

        _playerSlotsTable.setBackground(new Image(new NinePatch(Utility.STATUSUI_TEXTUREATLAS.createPatch("dialog"))).getDrawable());

        //layout
        for (int i = 1; i <= _numSlots; i++) {
            InventorySlot inventorySlot = new InventorySlot();
            inventorySlot.addListener(new InventorySlotTooltipListener(_inventorySlotTooltip));
            _dragAndDrop.addTarget(new InventorySlotTarget(inventorySlot));

            _inventorySlotTable.add(inventorySlot).size(_slotWidth, _slotHeight);

            if (i % _lengthSlotRow == 0) {
                _inventorySlotTable.row();
            }
        }

        _equipSlots.add();
        _equipSlots.add(headSlot).size(_slotWidth, _slotHeight);
        _equipSlots.row();

        _equipSlots.add(leftArmSlot).size(_slotWidth, _slotHeight);
        _equipSlots.add(chestSlot).size(_slotWidth, _slotHeight);
        _equipSlots.add(rightArmSlot).size(_slotWidth, _slotHeight);
        _equipSlots.row();

        _equipSlots.add();
        _equipSlots.right().add(legsSlot).size(_slotWidth, _slotHeight);

        _playerSlotsTable.add(_equipSlots);

        _inventoryActors.add(_inventorySlotTooltip);

        this.add(_playerSlotsTable).padBottom(20).row();
        this.add(_inventorySlotTable).row();
        this.pack();
    }

    public Table getInventorySlotTable() {
        return _inventorySlotTable;
    }

    public Table getEquipSlotTable() {
        return _equipSlots;
    }

    public void populateInventory(Table targetTable, Array<InventoryItemLocation> inventoryItems) {
        Array<Cell> cells = targetTable.getCells();
        for (int i = 0; i < inventoryItems.size; i++) {
            InventoryItemLocation itemLocation = inventoryItems.get(i);
            InventoryItem.ItemTypeID itemTypeID = InventoryItem.ItemTypeID.valueOf(itemLocation.getItemTypeAtLocation());
            InventorySlot inventorySlot = ((InventorySlot) cells.get(itemLocation.getLocationIndex()).getActor());
            inventorySlot.clearAllInventoryItems();

            for (int index = 0; index < itemLocation.getNumberItemsAtLocation(); index++) {
                inventorySlot.add(InventoryItemFactory.getInstance().getInventoryItem(itemTypeID));
                _dragAndDrop.addSource(new InventorySlotSource(inventorySlot, _dragAndDrop));
            }
        }
    }

    public Array<InventoryItemLocation> getInventory(Table targetTable) {
        Array<Cell> cells = targetTable.getCells();
        Array<InventoryItemLocation> items = new Array<InventoryItemLocation>();
        for (int i = 0; i < cells.size; i++) {
            InventorySlot inventorySlot = ((InventorySlot) cells.get(i).getActor());
            if (inventorySlot == null) continue;
            int numItems = inventorySlot.getNumItems();
            if (numItems > 0) {
                items.add(new InventoryItemLocation(
                        i,
                        inventorySlot.getTopInventoryItem().getItemTypeID().toString(),
                        numItems));
            }
        }
        return items;
    }

    public Array<Actor> getInventoryActors() {
        return _inventoryActors;
    }
}
