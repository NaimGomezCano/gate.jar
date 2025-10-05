/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.thin.extn.variant.SysBOEnum
 */
package com.sap.b1.thin.extn.variant;

public enum SysBOEnum {
    BusinessPartners("OCRD"),
    Activities("OCLG"),
    Quotations("OQUT"),
    Orders("ORDR"),
    Drafts("ODRF"),
    ApprovalRequests("OWDD"),
    ReserveInvoices("OINV_RV"),
    DeliveryNotes("ODLN"),
    Invoices("OINV"),
    Returns("ORDN"),
    CreditNotes("ORIN"),
    Items("OITM"),
    PurchaseOrders("OPOR"),
    PurchaseQuotations("OPQT"),
    PurchaseDeliveryNotes("OPDN"),
    PurchaseInvoices("OPCH"),
    PurchaseReserveInvoices("OPCH_RV"),
    PurchaseReturns("ORPD"),
    PurchaseCreditNotes("ORPC"),
    ServiceCall("OSCL"),
    Opportunities("OOPR"),
    BusinessPartnerCatalogNumbers("OSCN"),
    IncomingPayments("ORCT"),
    JournalEntries("OJDT"),
    TimeSheets("OTSH"),
    OutgoingPayments("OVPM"),
    UserQueries("OUQR"),
    SolutionsKnowledgeBase("OSLT"),
    InventoryGenExit("OIGE"),
    CustomerEquipmentCards("OINS"),
    ApprovalDecisions("VOWDDAPD"),
    OutgoingPaymentsInApprovalProcess("VOWDDPM"),
    ApprovalDecisionsforOutgoingPayments("VOWDDAPP"),
    ReturnRequest("ORRR"),
    GoodsReturnRequest("OPRR"),
    PurchaseRequests("OPRQ"),
    InventoryCounting("OINC"),
    ServiceContracts("OCTR"),
    InventoryGenEntry("OIGN"),
    ProductionOrders("OWOR"),
    IssueForProduction("VOIGEP"),
    ReceiptFromProduction("VOIGNP"),
    BillOfMaterial("OITT"),
    UserDefinedFields("CUFD"),
    AlternativeItems("OALI"),
    InventoryTransfer("OWTR"),
    InventoryTransferRequest("OWTQ"),
    RecurringPostings("ORCR"),
    JournalVoucher("OBTD"),
    ChartOfAccounts("OACT"),
    Configuration("Config"),
    UserDefinedValues("CUDV"),
    ExchangeRates("ORTT"),
    ExchangeRatesIndexes("ORTT"),
    SalesBlanketAgreements("OOAT"),
    ServiceContractTemplates("OCTT"),
    ServiceContractQueues("OQUE"),
    PurchaseBlanketAgreements("OOAT"),
    ActivitiesRecipientsLists("ORCI"),
    AccountCategories("OACG"),
    RecurringTransactionsTemplates("ORCP"),
    SalesAndPurchasingLandedCosts("OALC"),
    Deposits("ODPS"),
    RecurringTransactions("ORCL"),
    HouseBankAccounts("DSC1"),
    CreditCardPayments("OCDT"),
    Warehouses("OWHS"),
    JournalEntryTemplates("OTRT"),
    ReferenceFieldLinks("ORLD"),
    ItemGroups("OITB"),
    ARDownPaymentInvoices("ODPI"),
    APDownPaymentInvoices("ODPO"),
    InventoryCountingDrafts("OICD"),
    LandedCosts("OIPF");

    private String webclientTableName;

    private SysBOEnum(String tableName) {
        this.webclientTableName = tableName;
    }

    public String GetTableName() {
        return this.webclientTableName;
    }
}

