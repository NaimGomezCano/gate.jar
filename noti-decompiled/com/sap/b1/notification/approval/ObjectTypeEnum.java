/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.notification.approval.ObjectTypeEnum
 *  com.sap.b1.notification.base.IBaseEnum
 */
package com.sap.b1.notification.approval;

import com.sap.b1.notification.base.IBaseEnum;

public enum ObjectTypeEnum implements IBaseEnum<Integer>
{
    SalesOrder(17),
    SalesQuotation(23),
    Delivery(15),
    Invoice(13),
    Returns(16),
    ARCreditMemo(14),
    PurchaseRequest(1470000113),
    PurchaseQutation(540000006),
    PurchaseOrder(22),
    SalesDraft(112),
    GoodsReturn(21),
    APCreditMemos(19),
    ReturnRequest(234000031),
    GoodsReturnRequest(234000032),
    GRPO(20),
    APInvoice(18),
    OutgoingPayment(46),
    OPDF(140),
    GoodsIssue(60),
    GoodsReceipt(59),
    InventoryCounting(1470000065),
    InventoryCountingDraft(1470000109);

    private final int value;

    private ObjectTypeEnum(int value) {
        this.value = value;
    }

    public Integer getValue() {
        return this.value;
    }

    public String toString() {
        return String.valueOf(this.value);
    }
}

