/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.b1.extn.config.SvclConfig
 *  com.sap.b1.extn.config.SvclRequestInterceptor
 *  com.sap.b1.svcl.client.ServiceLayerClient
 *  com.sap.b1.svcl.client.entityset.AccountCategory
 *  com.sap.b1.svcl.client.entityset.AccountSegmentationCategories
 *  com.sap.b1.svcl.client.entityset.AccountSegmentations
 *  com.sap.b1.svcl.client.entityset.AccrualTypes
 *  com.sap.b1.svcl.client.entityset.Activities
 *  com.sap.b1.svcl.client.entityset.ActivityLocations
 *  com.sap.b1.svcl.client.entityset.ActivityRecipientLists
 *  com.sap.b1.svcl.client.entityset.ActivityStatuses
 *  com.sap.b1.svcl.client.entityset.ActivityTypes
 *  com.sap.b1.svcl.client.entityset.AdditionalExpenses
 *  com.sap.b1.svcl.client.entityset.AlertManagements
 *  com.sap.b1.svcl.client.entityset.AlternateCatNum
 *  com.sap.b1.svcl.client.entityset.ApprovalRequests
 *  com.sap.b1.svcl.client.entityset.ApprovalStages
 *  com.sap.b1.svcl.client.entityset.ApprovalTemplates
 *  com.sap.b1.svcl.client.entityset.AssetCapitalization
 *  com.sap.b1.svcl.client.entityset.AssetCapitalizationCreditMemo
 *  com.sap.b1.svcl.client.entityset.AssetClasses
 *  com.sap.b1.svcl.client.entityset.AssetDepreciationGroups
 *  com.sap.b1.svcl.client.entityset.AssetGroups
 *  com.sap.b1.svcl.client.entityset.AssetManualDepreciation
 *  com.sap.b1.svcl.client.entityset.AssetRetirement
 *  com.sap.b1.svcl.client.entityset.AssetTransfer
 *  com.sap.b1.svcl.client.entityset.Attachments2
 *  com.sap.b1.svcl.client.entityset.AttributeGroups
 *  com.sap.b1.svcl.client.entityset.B1Sessions
 *  com.sap.b1.svcl.client.entityset.BOEDocumentTypes
 *  com.sap.b1.svcl.client.entityset.BOEInstructions
 *  com.sap.b1.svcl.client.entityset.BOEPortfolios
 *  com.sap.b1.svcl.client.entityset.BPPriorities
 *  com.sap.b1.svcl.client.entityset.BankChargesAllocationCodes
 *  com.sap.b1.svcl.client.entityset.BankPages
 *  com.sap.b1.svcl.client.entityset.BankStatements
 *  com.sap.b1.svcl.client.entityset.Banks
 *  com.sap.b1.svcl.client.entityset.BarCodes
 *  com.sap.b1.svcl.client.entityset.BatchNumberDetails
 *  com.sap.b1.svcl.client.entityset.BillOfExchangeTransactions
 *  com.sap.b1.svcl.client.entityset.BinLocationAttributes
 *  com.sap.b1.svcl.client.entityset.BinLocationFields
 *  com.sap.b1.svcl.client.entityset.BinLocations
 *  com.sap.b1.svcl.client.entityset.BlanketAgreements
 *  com.sap.b1.svcl.client.entityset.Branches
 *  com.sap.b1.svcl.client.entityset.BrazilBeverageIndexers
 *  com.sap.b1.svcl.client.entityset.BrazilFuelIndexers
 *  com.sap.b1.svcl.client.entityset.BrazilMultiIndexers
 *  com.sap.b1.svcl.client.entityset.BrazilNumericIndexers
 *  com.sap.b1.svcl.client.entityset.BrazilStringIndexers
 *  com.sap.b1.svcl.client.entityset.BudgetDistributions
 *  com.sap.b1.svcl.client.entityset.BudgetScenarios
 *  com.sap.b1.svcl.client.entityset.Budgets
 *  com.sap.b1.svcl.client.entityset.BusinessPartnerGroups
 *  com.sap.b1.svcl.client.entityset.BusinessPartnerProperties
 *  com.sap.b1.svcl.client.entityset.BusinessPartners
 *  com.sap.b1.svcl.client.entityset.BusinessPlaces
 *  com.sap.b1.svcl.client.entityset.CampaignResponseType
 *  com.sap.b1.svcl.client.entityset.Campaigns
 *  com.sap.b1.svcl.client.entityset.CashDiscounts
 *  com.sap.b1.svcl.client.entityset.CashFlowLineItems
 *  com.sap.b1.svcl.client.entityset.CertificateSeries
 *  com.sap.b1.svcl.client.entityset.ChartOfAccounts
 *  com.sap.b1.svcl.client.entityset.ChecksforPayment
 *  com.sap.b1.svcl.client.entityset.ChooseFromList
 *  com.sap.b1.svcl.client.entityset.Cockpits
 *  com.sap.b1.svcl.client.entityset.CommissionGroups
 *  com.sap.b1.svcl.client.entityset.Contacts
 *  com.sap.b1.svcl.client.entityset.ContractTemplates
 *  com.sap.b1.svcl.client.entityset.CorrectionInvoice
 *  com.sap.b1.svcl.client.entityset.CorrectionInvoiceReversal
 *  com.sap.b1.svcl.client.entityset.CorrectionPurchaseInvoice
 *  com.sap.b1.svcl.client.entityset.CorrectionPurchaseInvoiceReversal
 *  com.sap.b1.svcl.client.entityset.CostCenterTypes
 *  com.sap.b1.svcl.client.entityset.CostElements
 *  com.sap.b1.svcl.client.entityset.Countries
 *  com.sap.b1.svcl.client.entityset.CreditCardPayments
 *  com.sap.b1.svcl.client.entityset.CreditCards
 *  com.sap.b1.svcl.client.entityset.CreditNotes
 *  com.sap.b1.svcl.client.entityset.CreditPaymentMethods
 *  com.sap.b1.svcl.client.entityset.Currencies
 *  com.sap.b1.svcl.client.entityset.CustomerEquipmentCards
 *  com.sap.b1.svcl.client.entityset.CustomsDeclaration
 *  com.sap.b1.svcl.client.entityset.CustomsGroups
 *  com.sap.b1.svcl.client.entityset.CycleCountDeterminations
 *  com.sap.b1.svcl.client.entityset.DNFCodeSetup
 *  com.sap.b1.svcl.client.entityset.DeductionTaxGroups
 *  com.sap.b1.svcl.client.entityset.DeductionTaxHierarchies
 *  com.sap.b1.svcl.client.entityset.DeductionTaxSubGroups
 *  com.sap.b1.svcl.client.entityset.DeliveryNotes
 *  com.sap.b1.svcl.client.entityset.Departments
 *  com.sap.b1.svcl.client.entityset.Deposits
 *  com.sap.b1.svcl.client.entityset.DepreciationAreas
 *  com.sap.b1.svcl.client.entityset.DepreciationTypePools
 *  com.sap.b1.svcl.client.entityset.DepreciationTypes
 *  com.sap.b1.svcl.client.entityset.DeterminationCriterias
 *  com.sap.b1.svcl.client.entityset.Dimensions
 *  com.sap.b1.svcl.client.entityset.DistributionRules
 *  com.sap.b1.svcl.client.entityset.DownPayments
 *  com.sap.b1.svcl.client.entityset.Drafts
 *  com.sap.b1.svcl.client.entityset.DunningLetters
 *  com.sap.b1.svcl.client.entityset.DunningTerms
 *  com.sap.b1.svcl.client.entityset.DynamicSystemStrings
 *  com.sap.b1.svcl.client.entityset.ElectronicFileFormats
 *  com.sap.b1.svcl.client.entityset.EmailGroups
 *  com.sap.b1.svcl.client.entityset.EmployeeIDType
 *  com.sap.b1.svcl.client.entityset.EmployeePosition
 *  com.sap.b1.svcl.client.entityset.EmployeeRolesSetup
 *  com.sap.b1.svcl.client.entityset.EmployeeStatus
 *  com.sap.b1.svcl.client.entityset.EmployeeTransfers
 *  com.sap.b1.svcl.client.entityset.EmployeesInfo
 *  com.sap.b1.svcl.client.entityset.EnhancedDiscountGroups
 *  com.sap.b1.svcl.client.entityset.ExpenseTypes
 *  com.sap.b1.svcl.client.entityset.ExtendedTranslations
 *  com.sap.b1.svcl.client.entityset.FAAccountDeterminations
 *  com.sap.b1.svcl.client.entityset.FactoringIndicators
 *  com.sap.b1.svcl.client.entityset.FinancialYears
 *  com.sap.b1.svcl.client.entityset.FiscalPrinter
 *  com.sap.b1.svcl.client.entityset.FormPreferences
 *  com.sap.b1.svcl.client.entityset.FormattedSearches
 *  com.sap.b1.svcl.client.entityset.Forms1099
 *  com.sap.b1.svcl.client.entityset.GLAccountAdvancedRules
 *  com.sap.b1.svcl.client.entityset.GoodsReturnRequest
 *  com.sap.b1.svcl.client.entityset.GovPayCodes
 *  com.sap.b1.svcl.client.entityset.HouseBankAccounts
 *  com.sap.b1.svcl.client.entityset.IncomingPayments
 *  com.sap.b1.svcl.client.entityset.IndiaSacCode
 *  com.sap.b1.svcl.client.entityset.Industries
 *  com.sap.b1.svcl.client.entityset.IntegrationPackagesConfigure
 *  com.sap.b1.svcl.client.entityset.InternalReconciliations
 *  com.sap.b1.svcl.client.entityset.IntrastatConfiguration
 *  com.sap.b1.svcl.client.entityset.InventoryCountings
 *  com.sap.b1.svcl.client.entityset.InventoryCycles
 *  com.sap.b1.svcl.client.entityset.InventoryGenEntries
 *  com.sap.b1.svcl.client.entityset.InventoryGenExits
 *  com.sap.b1.svcl.client.entityset.InventoryOpeningBalances
 *  com.sap.b1.svcl.client.entityset.InventoryPostings
 *  com.sap.b1.svcl.client.entityset.InventoryTransferRequests
 *  com.sap.b1.svcl.client.entityset.Invoices
 *  com.sap.b1.svcl.client.entityset.ItemGroups
 *  com.sap.b1.svcl.client.entityset.ItemImages
 *  com.sap.b1.svcl.client.entityset.ItemProperties
 *  com.sap.b1.svcl.client.entityset.Items
 *  com.sap.b1.svcl.client.entityset.JournalEntries
 *  com.sap.b1.svcl.client.entityset.JournalEntryDocumentTypes
 *  com.sap.b1.svcl.client.entityset.KPIs
 *  com.sap.b1.svcl.client.entityset.KnowledgeBaseSolutions
 *  com.sap.b1.svcl.client.entityset.LandedCosts
 *  com.sap.b1.svcl.client.entityset.LandedCostsCodes
 *  com.sap.b1.svcl.client.entityset.LegalData
 *  com.sap.b1.svcl.client.entityset.LengthMeasures
 *  com.sap.b1.svcl.client.entityset.LocalEra
 *  com.sap.b1.svcl.client.entityset.Manufacturers
 *  com.sap.b1.svcl.client.entityset.MaterialGroups
 *  com.sap.b1.svcl.client.entityset.MaterialRevaluation
 *  com.sap.b1.svcl.client.entityset.Messages
 *  com.sap.b1.svcl.client.entityset.MobileAddOnSetting
 *  com.sap.b1.svcl.client.entityset.MultiLanguageTranslations
 *  com.sap.b1.svcl.client.entityset.NCMCodesSetup
 *  com.sap.b1.svcl.client.entityset.NFModels
 *  com.sap.b1.svcl.client.entityset.NFTaxCategories
 *  com.sap.b1.svcl.client.entityset.NatureOfAssessees
 *  com.sap.b1.svcl.client.entityset.OccurrenceCodes
 *  com.sap.b1.svcl.client.entityset.Orders
 *  com.sap.b1.svcl.client.entityset.POSDailySummary
 *  com.sap.b1.svcl.client.entityset.PackagesTypes
 *  com.sap.b1.svcl.client.entityset.PartnersSetups
 *  com.sap.b1.svcl.client.entityset.PaymentBlocks
 *  com.sap.b1.svcl.client.entityset.PaymentDrafts
 *  com.sap.b1.svcl.client.entityset.PaymentRunExport
 *  com.sap.b1.svcl.client.entityset.PaymentTermsTypes
 *  com.sap.b1.svcl.client.entityset.PickLists
 *  com.sap.b1.svcl.client.entityset.PredefinedTexts
 *  com.sap.b1.svcl.client.entityset.PriceLists
 *  com.sap.b1.svcl.client.entityset.ProductTrees
 *  com.sap.b1.svcl.client.entityset.ProductionOrders
 *  com.sap.b1.svcl.client.entityset.ProfitCenters
 *  com.sap.b1.svcl.client.entityset.ProjectManagementTimeSheet
 *  com.sap.b1.svcl.client.entityset.ProjectManagements
 *  com.sap.b1.svcl.client.entityset.Projects
 *  com.sap.b1.svcl.client.entityset.PurchaseCreditNotes
 *  com.sap.b1.svcl.client.entityset.PurchaseDeliveryNotes
 *  com.sap.b1.svcl.client.entityset.PurchaseDownPayments
 *  com.sap.b1.svcl.client.entityset.PurchaseInvoices
 *  com.sap.b1.svcl.client.entityset.PurchaseOrders
 *  com.sap.b1.svcl.client.entityset.PurchaseQuotations
 *  com.sap.b1.svcl.client.entityset.PurchaseRequests
 *  com.sap.b1.svcl.client.entityset.PurchaseReturns
 *  com.sap.b1.svcl.client.entityset.PurchaseTaxInvoices
 *  com.sap.b1.svcl.client.entityset.QueryAuthGroups
 *  com.sap.b1.svcl.client.entityset.QueryCategories
 *  com.sap.b1.svcl.client.entityset.Queue
 *  com.sap.b1.svcl.client.entityset.Quotations
 *  com.sap.b1.svcl.client.entityset.Relationships
 *  com.sap.b1.svcl.client.entityset.ReportFilter
 *  com.sap.b1.svcl.client.entityset.ReportTypes
 *  com.sap.b1.svcl.client.entityset.ResourceCapacities
 *  com.sap.b1.svcl.client.entityset.ResourceGroups
 *  com.sap.b1.svcl.client.entityset.ResourceProperties
 *  com.sap.b1.svcl.client.entityset.Resources
 *  com.sap.b1.svcl.client.entityset.RetornoCodes
 *  com.sap.b1.svcl.client.entityset.ReturnRequest
 *  com.sap.b1.svcl.client.entityset.Returns
 *  com.sap.b1.svcl.client.entityset.RouteStages
 *  com.sap.b1.svcl.client.entityset.SalesForecast
 *  com.sap.b1.svcl.client.entityset.SalesOpportunities
 *  com.sap.b1.svcl.client.entityset.SalesOpportunityCompetitorsSetup
 *  com.sap.b1.svcl.client.entityset.SalesOpportunityInterestsSetup
 *  com.sap.b1.svcl.client.entityset.SalesOpportunityReasonsSetup
 *  com.sap.b1.svcl.client.entityset.SalesOpportunitySourcesSetup
 *  com.sap.b1.svcl.client.entityset.SalesPersons
 *  com.sap.b1.svcl.client.entityset.SalesStages
 *  com.sap.b1.svcl.client.entityset.SalesTaxAuthorities
 *  com.sap.b1.svcl.client.entityset.SalesTaxAuthoritiesTypes
 *  com.sap.b1.svcl.client.entityset.SalesTaxCodes
 *  com.sap.b1.svcl.client.entityset.SalesTaxInvoices
 *  com.sap.b1.svcl.client.entityset.Sections
 *  com.sap.b1.svcl.client.entityset.SerialNumberDetails
 *  com.sap.b1.svcl.client.entityset.ServiceCallOrigins
 *  com.sap.b1.svcl.client.entityset.ServiceCallProblemSubTypes
 *  com.sap.b1.svcl.client.entityset.ServiceCallProblemTypes
 *  com.sap.b1.svcl.client.entityset.ServiceCallSolutionStatus
 *  com.sap.b1.svcl.client.entityset.ServiceCallStatus
 *  com.sap.b1.svcl.client.entityset.ServiceCallTypes
 *  com.sap.b1.svcl.client.entityset.ServiceCalls
 *  com.sap.b1.svcl.client.entityset.ServiceContracts
 *  com.sap.b1.svcl.client.entityset.ServiceGroups
 *  com.sap.b1.svcl.client.entityset.ShippingTypes
 *  com.sap.b1.svcl.client.entityset.SpecialPrices
 *  com.sap.b1.svcl.client.entityset.States
 *  com.sap.b1.svcl.client.entityset.StockTakings
 *  com.sap.b1.svcl.client.entityset.StockTransferDrafts
 *  com.sap.b1.svcl.client.entityset.StockTransfers
 *  com.sap.b1.svcl.client.entityset.TargetGroups
 *  com.sap.b1.svcl.client.entityset.TaxCodeDeterminations
 *  com.sap.b1.svcl.client.entityset.TaxCodeDeterminationsTCD
 *  com.sap.b1.svcl.client.entityset.TaxInvoiceReport
 *  com.sap.b1.svcl.client.entityset.TaxWebSites
 *  com.sap.b1.svcl.client.entityset.Teams
 *  com.sap.b1.svcl.client.entityset.TerminationReason
 *  com.sap.b1.svcl.client.entityset.Territories
 *  com.sap.b1.svcl.client.entityset.TrackingNotes
 *  com.sap.b1.svcl.client.entityset.TransactionCodes
 *  com.sap.b1.svcl.client.entityset.TransportationDocument
 *  com.sap.b1.svcl.client.entityset.UnitOfMeasurementGroups
 *  com.sap.b1.svcl.client.entityset.UnitOfMeasurements
 *  com.sap.b1.svcl.client.entityset.UserDefaultGroups
 *  com.sap.b1.svcl.client.entityset.UserFieldsMD
 *  com.sap.b1.svcl.client.entityset.UserGroups
 *  com.sap.b1.svcl.client.entityset.UserKeysMD
 *  com.sap.b1.svcl.client.entityset.UserLanguages
 *  com.sap.b1.svcl.client.entityset.UserObjectsMD
 *  com.sap.b1.svcl.client.entityset.UserPermissionTree
 *  com.sap.b1.svcl.client.entityset.UserQueries
 *  com.sap.b1.svcl.client.entityset.UserTablesMD
 *  com.sap.b1.svcl.client.entityset.Users
 *  com.sap.b1.svcl.client.entityset.ValueMapping
 *  com.sap.b1.svcl.client.entityset.ValueMappingCommunication
 *  com.sap.b1.svcl.client.entityset.VatGroups
 *  com.sap.b1.svcl.client.entityset.VendorPayments
 *  com.sap.b1.svcl.client.entityset.WarehouseLocations
 *  com.sap.b1.svcl.client.entityset.WarehouseSublevelCodes
 *  com.sap.b1.svcl.client.entityset.Warehouses
 *  com.sap.b1.svcl.client.entityset.WebClientBookmarkTiles
 *  com.sap.b1.svcl.client.entityset.WebClientDashboards
 *  com.sap.b1.svcl.client.entityset.WebClientFormSettings
 *  com.sap.b1.svcl.client.entityset.WebClientLaunchpads
 *  com.sap.b1.svcl.client.entityset.WebClientListviewFilters
 *  com.sap.b1.svcl.client.entityset.WebClientNotifications
 *  com.sap.b1.svcl.client.entityset.WebClientPreferences
 *  com.sap.b1.svcl.client.entityset.WebClientRecentActivities
 *  com.sap.b1.svcl.client.entityset.WebClientVariantGroups
 *  com.sap.b1.svcl.client.entityset.WebClientVariants
 *  com.sap.b1.svcl.client.entityset.WeightMeasures
 *  com.sap.b1.svcl.client.entityset.WithholdingTaxCodes
 *  com.sap.b1.svcl.client.entityset.WitholdingTaxDefinition
 *  com.sap.b1.svcl.client.entityset.WizardPaymentMethods
 *  com.sap.b1.svcl.client.functionimport.AccountCategoryService
 *  com.sap.b1.svcl.client.functionimport.AccountsService
 *  com.sap.b1.svcl.client.functionimport.AccrualTypesService
 *  com.sap.b1.svcl.client.functionimport.ActivitiesService
 *  com.sap.b1.svcl.client.functionimport.ActivityRecipientListsService
 *  com.sap.b1.svcl.client.functionimport.AlternativeItemsService
 *  com.sap.b1.svcl.client.functionimport.ApprovalRequestsService
 *  com.sap.b1.svcl.client.functionimport.ApprovalStagesService
 *  com.sap.b1.svcl.client.functionimport.ApprovalTemplatesService
 *  com.sap.b1.svcl.client.functionimport.AssetCapitalizationCreditMemoService
 *  com.sap.b1.svcl.client.functionimport.AssetCapitalizationService
 *  com.sap.b1.svcl.client.functionimport.AssetClassesService
 *  com.sap.b1.svcl.client.functionimport.AssetDepreciationGroupsService
 *  com.sap.b1.svcl.client.functionimport.AssetGroupsService
 *  com.sap.b1.svcl.client.functionimport.AssetManualDepreciationService
 *  com.sap.b1.svcl.client.functionimport.AssetRetirementService
 *  com.sap.b1.svcl.client.functionimport.AssetTransferService
 *  com.sap.b1.svcl.client.functionimport.AttributeGroupsService
 *  com.sap.b1.svcl.client.functionimport.BOEDocumentTypesService
 *  com.sap.b1.svcl.client.functionimport.BOEInstructionsService
 *  com.sap.b1.svcl.client.functionimport.BOELinesService
 *  com.sap.b1.svcl.client.functionimport.BOEPortfoliosService
 *  com.sap.b1.svcl.client.functionimport.BPOpeningBalanceService
 *  com.sap.b1.svcl.client.functionimport.BankChargesAllocationCodesService
 *  com.sap.b1.svcl.client.functionimport.BankStatementsService
 *  com.sap.b1.svcl.client.functionimport.BarCodesService
 *  com.sap.b1.svcl.client.functionimport.BinLocationAttributesService
 *  com.sap.b1.svcl.client.functionimport.BinLocationFieldsService
 *  com.sap.b1.svcl.client.functionimport.BinLocationsService
 *  com.sap.b1.svcl.client.functionimport.BlanketAgreementsService
 *  com.sap.b1.svcl.client.functionimport.BranchesService
 *  com.sap.b1.svcl.client.functionimport.BrazilBeverageIndexersService
 *  com.sap.b1.svcl.client.functionimport.BrazilFuelIndexersService
 *  com.sap.b1.svcl.client.functionimport.BusinessPartnerPropertiesService
 *  com.sap.b1.svcl.client.functionimport.BusinessPartnersService
 *  com.sap.b1.svcl.client.functionimport.CampaignResponseTypeService
 *  com.sap.b1.svcl.client.functionimport.CampaignsService
 *  com.sap.b1.svcl.client.functionimport.CashDiscountsService
 *  com.sap.b1.svcl.client.functionimport.CashFlowLineItemsService
 *  com.sap.b1.svcl.client.functionimport.CertificateSeriesService
 *  com.sap.b1.svcl.client.functionimport.ChangeLogsService
 *  com.sap.b1.svcl.client.functionimport.CheckLinesService
 *  com.sap.b1.svcl.client.functionimport.CockpitsService
 *  com.sap.b1.svcl.client.functionimport.CompanyService
 *  com.sap.b1.svcl.client.functionimport.CorrectionInvoiceReversalService
 *  com.sap.b1.svcl.client.functionimport.CorrectionInvoiceService
 *  com.sap.b1.svcl.client.functionimport.CorrectionPurchaseInvoiceReversalService
 *  com.sap.b1.svcl.client.functionimport.CorrectionPurchaseInvoiceService
 *  com.sap.b1.svcl.client.functionimport.CostCenterTypesService
 *  com.sap.b1.svcl.client.functionimport.CostElementService
 *  com.sap.b1.svcl.client.functionimport.CountriesService
 *  com.sap.b1.svcl.client.functionimport.CreditLinesService
 *  com.sap.b1.svcl.client.functionimport.CreditNotesService
 *  com.sap.b1.svcl.client.functionimport.CycleCountDeterminationsService
 *  com.sap.b1.svcl.client.functionimport.DNFCodeSetupService
 *  com.sap.b1.svcl.client.functionimport.DashboardPackagesService
 *  com.sap.b1.svcl.client.functionimport.DeductionTaxSubGroupsService
 *  com.sap.b1.svcl.client.functionimport.DeliveryNotesService
 *  com.sap.b1.svcl.client.functionimport.DepartmentsService
 *  com.sap.b1.svcl.client.functionimport.DepositsService
 *  com.sap.b1.svcl.client.functionimport.DepreciationAreasService
 *  com.sap.b1.svcl.client.functionimport.DepreciationTypePoolsService
 *  com.sap.b1.svcl.client.functionimport.DepreciationTypesService
 *  com.sap.b1.svcl.client.functionimport.DeterminationCriteriasService
 *  com.sap.b1.svcl.client.functionimport.DimensionsService
 *  com.sap.b1.svcl.client.functionimport.DistributionRulesService
 *  com.sap.b1.svcl.client.functionimport.DownPaymentsService
 *  com.sap.b1.svcl.client.functionimport.DraftsService
 *  com.sap.b1.svcl.client.functionimport.DunningTermsService
 *  com.sap.b1.svcl.client.functionimport.ElectronicCommunicationActionService
 *  com.sap.b1.svcl.client.functionimport.ElectronicCommunicationActionsService
 *  com.sap.b1.svcl.client.functionimport.ElectronicFileFormatsService
 *  com.sap.b1.svcl.client.functionimport.EmailGroupsService
 *  com.sap.b1.svcl.client.functionimport.EmployeeIDTypeService
 *  com.sap.b1.svcl.client.functionimport.EmployeePositionService
 *  com.sap.b1.svcl.client.functionimport.EmployeeRolesSetupService
 *  com.sap.b1.svcl.client.functionimport.EmployeeStatusService
 *  com.sap.b1.svcl.client.functionimport.EmployeeTransfersService
 *  com.sap.b1.svcl.client.functionimport.EnhancedDiscountGroupsService
 *  com.sap.b1.svcl.client.functionimport.ExtendedTranslationsService
 *  com.sap.b1.svcl.client.functionimport.ExternalCallsService
 *  com.sap.b1.svcl.client.functionimport.ExternalReconciliationsService
 *  com.sap.b1.svcl.client.functionimport.FAAccountDeterminationsService
 *  com.sap.b1.svcl.client.functionimport.FinancialYearsService
 *  com.sap.b1.svcl.client.functionimport.FiscalPrinterService
 *  com.sap.b1.svcl.client.functionimport.FixedAssetItemsService
 *  com.sap.b1.svcl.client.functionimport.GLAccountAdvancedRulesService
 *  com.sap.b1.svcl.client.functionimport.GTIsService
 *  com.sap.b1.svcl.client.functionimport.GoodsReturnRequestService
 *  com.sap.b1.svcl.client.functionimport.GovPayCodesService
 *  com.sap.b1.svcl.client.functionimport.IndiaSacCodeService
 *  com.sap.b1.svcl.client.functionimport.IntegrationPackagesConfigureService
 *  com.sap.b1.svcl.client.functionimport.InternalReconciliationsService
 *  com.sap.b1.svcl.client.functionimport.IntrastatConfigurationService
 *  com.sap.b1.svcl.client.functionimport.InventoryCountingsService
 *  com.sap.b1.svcl.client.functionimport.InventoryGenEntryService
 *  com.sap.b1.svcl.client.functionimport.InventoryGenExitService
 *  com.sap.b1.svcl.client.functionimport.InventoryOpeningBalancesService
 *  com.sap.b1.svcl.client.functionimport.InventoryPostingsService
 *  com.sap.b1.svcl.client.functionimport.InventoryTransferRequestsService
 *  com.sap.b1.svcl.client.functionimport.InvoicesService
 *  com.sap.b1.svcl.client.functionimport.JournalEntryDocumentTypeService
 *  com.sap.b1.svcl.client.functionimport.JournalVouchersService
 *  com.sap.b1.svcl.client.functionimport.KPIsService
 *  com.sap.b1.svcl.client.functionimport.LandedCostsService
 *  com.sap.b1.svcl.client.functionimport.LicenseService
 *  com.sap.b1.svcl.client.functionimport.Login
 *  com.sap.b1.svcl.client.functionimport.Logout
 *  com.sap.b1.svcl.client.functionimport.MaterialGroupsService
 *  com.sap.b1.svcl.client.functionimport.MaterialRevaluationFIFOService
 *  com.sap.b1.svcl.client.functionimport.MaterialRevaluationSNBService
 *  com.sap.b1.svcl.client.functionimport.MessagesService
 *  com.sap.b1.svcl.client.functionimport.MobileAddOnSettingService
 *  com.sap.b1.svcl.client.functionimport.MobileAppService
 *  com.sap.b1.svcl.client.functionimport.NCMCodesSetupService
 *  com.sap.b1.svcl.client.functionimport.NFModelsService
 *  com.sap.b1.svcl.client.functionimport.NFTaxCategoriesService
 *  com.sap.b1.svcl.client.functionimport.NatureOfAssesseesService
 *  com.sap.b1.svcl.client.functionimport.OccurrenceCodesService
 *  com.sap.b1.svcl.client.functionimport.OrdersService
 *  com.sap.b1.svcl.client.functionimport.PartnersSetupsService
 *  com.sap.b1.svcl.client.functionimport.PaymentBlocksService
 *  com.sap.b1.svcl.client.functionimport.PaymentCalculationService
 *  com.sap.b1.svcl.client.functionimport.PaymentTermsTypesService
 *  com.sap.b1.svcl.client.functionimport.PickListsService
 *  com.sap.b1.svcl.client.functionimport.PredefinedTextsService
 *  com.sap.b1.svcl.client.functionimport.ProfitCentersService
 *  com.sap.b1.svcl.client.functionimport.ProjectManagementConfigurationService
 *  com.sap.b1.svcl.client.functionimport.ProjectManagementService
 *  com.sap.b1.svcl.client.functionimport.ProjectsService
 *  com.sap.b1.svcl.client.functionimport.PurchaseCreditNotesService
 *  com.sap.b1.svcl.client.functionimport.PurchaseDeliveryNotesService
 *  com.sap.b1.svcl.client.functionimport.PurchaseDownPaymentsService
 *  com.sap.b1.svcl.client.functionimport.PurchaseInvoicesService
 *  com.sap.b1.svcl.client.functionimport.PurchaseOrdersService
 *  com.sap.b1.svcl.client.functionimport.PurchaseQuotationsService
 *  com.sap.b1.svcl.client.functionimport.PurchaseRequestService
 *  com.sap.b1.svcl.client.functionimport.PurchaseReturnsService
 *  com.sap.b1.svcl.client.functionimport.QueryAuthGroupService
 *  com.sap.b1.svcl.client.functionimport.QueryService
 *  com.sap.b1.svcl.client.functionimport.QuotationsService
 *  com.sap.b1.svcl.client.functionimport.RecurringTransactionService
 *  com.sap.b1.svcl.client.functionimport.ReportFilterService
 *  com.sap.b1.svcl.client.functionimport.ReportLayoutsService
 *  com.sap.b1.svcl.client.functionimport.ReportTypesService
 *  com.sap.b1.svcl.client.functionimport.ResourceCapacitiesService
 *  com.sap.b1.svcl.client.functionimport.ResourceGroupsService
 *  com.sap.b1.svcl.client.functionimport.ResourcePropertiesService
 *  com.sap.b1.svcl.client.functionimport.ResourcesService
 *  com.sap.b1.svcl.client.functionimport.RetornoCodesService
 *  com.sap.b1.svcl.client.functionimport.ReturnRequestService
 *  com.sap.b1.svcl.client.functionimport.ReturnsService
 *  com.sap.b1.svcl.client.functionimport.RouteStagesService
 *  com.sap.b1.svcl.client.functionimport.RoutingDateCalculationService
 *  com.sap.b1.svcl.client.functionimport.SBOBobService
 *  com.sap.b1.svcl.client.functionimport.SalesOpportunityCompetitorsSetupService
 *  com.sap.b1.svcl.client.functionimport.SalesOpportunityInterestsSetupService
 *  com.sap.b1.svcl.client.functionimport.SalesOpportunityReasonsSetupService
 *  com.sap.b1.svcl.client.functionimport.SalesOpportunitySourcesSetupService
 *  com.sap.b1.svcl.client.functionimport.SectionsService
 *  com.sap.b1.svcl.client.functionimport.SensitiveDataAccessService
 *  com.sap.b1.svcl.client.functionimport.SeriesService
 *  com.sap.b1.svcl.client.functionimport.ServiceCallOriginsService
 *  com.sap.b1.svcl.client.functionimport.ServiceCallProblemSubTypesService
 *  com.sap.b1.svcl.client.functionimport.ServiceCallProblemTypesService
 *  com.sap.b1.svcl.client.functionimport.ServiceCallSolutionStatusService
 *  com.sap.b1.svcl.client.functionimport.ServiceCallStatusService
 *  com.sap.b1.svcl.client.functionimport.ServiceCallTypesService
 *  com.sap.b1.svcl.client.functionimport.ServiceGroupsService
 *  com.sap.b1.svcl.client.functionimport.ServiceTaxPostingService
 *  com.sap.b1.svcl.client.functionimport.StatesService
 *  com.sap.b1.svcl.client.functionimport.StockTransferDraftService
 *  com.sap.b1.svcl.client.functionimport.StockTransferService
 *  com.sap.b1.svcl.client.functionimport.TargetGroupsService
 *  com.sap.b1.svcl.client.functionimport.TaxCodeDeterminationsService
 *  com.sap.b1.svcl.client.functionimport.TaxCodeDeterminationsTCDService
 *  com.sap.b1.svcl.client.functionimport.TaxWebSitesService
 *  com.sap.b1.svcl.client.functionimport.TerminationReasonService
 *  com.sap.b1.svcl.client.functionimport.TrackingNotesService
 *  com.sap.b1.svcl.client.functionimport.TransactionCodesService
 *  com.sap.b1.svcl.client.functionimport.UnitOfMeasurementGroupsService
 *  com.sap.b1.svcl.client.functionimport.UnitOfMeasurementsService
 *  com.sap.b1.svcl.client.functionimport.UserGroupService
 *  com.sap.b1.svcl.client.functionimport.UserMenuService
 *  com.sap.b1.svcl.client.functionimport.ValueMappingService
 *  com.sap.b1.svcl.client.functionimport.WarehouseSublevelCodesService
 *  com.sap.b1.svcl.client.functionimport.WebClientBookmarkTileService
 *  com.sap.b1.svcl.client.functionimport.WebClientDashboardService
 *  com.sap.b1.svcl.client.functionimport.WebClientFormSettingService
 *  com.sap.b1.svcl.client.functionimport.WebClientLaunchpadService
 *  com.sap.b1.svcl.client.functionimport.WebClientListviewFilterService
 *  com.sap.b1.svcl.client.functionimport.WebClientNotificationService
 *  com.sap.b1.svcl.client.functionimport.WebClientPreferenceService
 *  com.sap.b1.svcl.client.functionimport.WebClientRecentActivityService
 *  com.sap.b1.svcl.client.functionimport.WebClientVariantGroupService
 *  com.sap.b1.svcl.client.functionimport.WebClientVariantService
 *  com.sap.b1.svcl.client.functionimport.WorkflowTaskService
 *  com.sap.b1.util.HttpClientPoolUtil
 *  feign.Client
 *  feign.Feign
 *  feign.Feign$Builder
 *  feign.Logger
 *  feign.Logger$Level
 *  feign.RequestInterceptor
 *  feign.hc5.ApacheHttp5Client
 *  feign.slf4j.Slf4jLogger
 *  org.apache.hc.client5.http.classic.HttpClient
 *  org.apache.hc.client5.http.impl.classic.CloseableHttpClient
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.beans.factory.annotation.Value
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 */
package com.sap.b1.extn.config;

import com.sap.b1.extn.config.SvclRequestInterceptor;
import com.sap.b1.svcl.client.ServiceLayerClient;
import com.sap.b1.svcl.client.entityset.AccountCategory;
import com.sap.b1.svcl.client.entityset.AccountSegmentationCategories;
import com.sap.b1.svcl.client.entityset.AccountSegmentations;
import com.sap.b1.svcl.client.entityset.AccrualTypes;
import com.sap.b1.svcl.client.entityset.Activities;
import com.sap.b1.svcl.client.entityset.ActivityLocations;
import com.sap.b1.svcl.client.entityset.ActivityRecipientLists;
import com.sap.b1.svcl.client.entityset.ActivityStatuses;
import com.sap.b1.svcl.client.entityset.ActivityTypes;
import com.sap.b1.svcl.client.entityset.AdditionalExpenses;
import com.sap.b1.svcl.client.entityset.AlertManagements;
import com.sap.b1.svcl.client.entityset.AlternateCatNum;
import com.sap.b1.svcl.client.entityset.ApprovalRequests;
import com.sap.b1.svcl.client.entityset.ApprovalStages;
import com.sap.b1.svcl.client.entityset.ApprovalTemplates;
import com.sap.b1.svcl.client.entityset.AssetCapitalization;
import com.sap.b1.svcl.client.entityset.AssetCapitalizationCreditMemo;
import com.sap.b1.svcl.client.entityset.AssetClasses;
import com.sap.b1.svcl.client.entityset.AssetDepreciationGroups;
import com.sap.b1.svcl.client.entityset.AssetGroups;
import com.sap.b1.svcl.client.entityset.AssetManualDepreciation;
import com.sap.b1.svcl.client.entityset.AssetRetirement;
import com.sap.b1.svcl.client.entityset.AssetTransfer;
import com.sap.b1.svcl.client.entityset.Attachments2;
import com.sap.b1.svcl.client.entityset.AttributeGroups;
import com.sap.b1.svcl.client.entityset.B1Sessions;
import com.sap.b1.svcl.client.entityset.BOEDocumentTypes;
import com.sap.b1.svcl.client.entityset.BOEInstructions;
import com.sap.b1.svcl.client.entityset.BOEPortfolios;
import com.sap.b1.svcl.client.entityset.BPPriorities;
import com.sap.b1.svcl.client.entityset.BankChargesAllocationCodes;
import com.sap.b1.svcl.client.entityset.BankPages;
import com.sap.b1.svcl.client.entityset.BankStatements;
import com.sap.b1.svcl.client.entityset.Banks;
import com.sap.b1.svcl.client.entityset.BarCodes;
import com.sap.b1.svcl.client.entityset.BatchNumberDetails;
import com.sap.b1.svcl.client.entityset.BillOfExchangeTransactions;
import com.sap.b1.svcl.client.entityset.BinLocationAttributes;
import com.sap.b1.svcl.client.entityset.BinLocationFields;
import com.sap.b1.svcl.client.entityset.BinLocations;
import com.sap.b1.svcl.client.entityset.BlanketAgreements;
import com.sap.b1.svcl.client.entityset.Branches;
import com.sap.b1.svcl.client.entityset.BrazilBeverageIndexers;
import com.sap.b1.svcl.client.entityset.BrazilFuelIndexers;
import com.sap.b1.svcl.client.entityset.BrazilMultiIndexers;
import com.sap.b1.svcl.client.entityset.BrazilNumericIndexers;
import com.sap.b1.svcl.client.entityset.BrazilStringIndexers;
import com.sap.b1.svcl.client.entityset.BudgetDistributions;
import com.sap.b1.svcl.client.entityset.BudgetScenarios;
import com.sap.b1.svcl.client.entityset.Budgets;
import com.sap.b1.svcl.client.entityset.BusinessPartnerGroups;
import com.sap.b1.svcl.client.entityset.BusinessPartnerProperties;
import com.sap.b1.svcl.client.entityset.BusinessPartners;
import com.sap.b1.svcl.client.entityset.BusinessPlaces;
import com.sap.b1.svcl.client.entityset.CampaignResponseType;
import com.sap.b1.svcl.client.entityset.Campaigns;
import com.sap.b1.svcl.client.entityset.CashDiscounts;
import com.sap.b1.svcl.client.entityset.CashFlowLineItems;
import com.sap.b1.svcl.client.entityset.CertificateSeries;
import com.sap.b1.svcl.client.entityset.ChartOfAccounts;
import com.sap.b1.svcl.client.entityset.ChecksforPayment;
import com.sap.b1.svcl.client.entityset.ChooseFromList;
import com.sap.b1.svcl.client.entityset.Cockpits;
import com.sap.b1.svcl.client.entityset.CommissionGroups;
import com.sap.b1.svcl.client.entityset.Contacts;
import com.sap.b1.svcl.client.entityset.ContractTemplates;
import com.sap.b1.svcl.client.entityset.CorrectionInvoice;
import com.sap.b1.svcl.client.entityset.CorrectionInvoiceReversal;
import com.sap.b1.svcl.client.entityset.CorrectionPurchaseInvoice;
import com.sap.b1.svcl.client.entityset.CorrectionPurchaseInvoiceReversal;
import com.sap.b1.svcl.client.entityset.CostCenterTypes;
import com.sap.b1.svcl.client.entityset.CostElements;
import com.sap.b1.svcl.client.entityset.Countries;
import com.sap.b1.svcl.client.entityset.CreditCardPayments;
import com.sap.b1.svcl.client.entityset.CreditCards;
import com.sap.b1.svcl.client.entityset.CreditNotes;
import com.sap.b1.svcl.client.entityset.CreditPaymentMethods;
import com.sap.b1.svcl.client.entityset.Currencies;
import com.sap.b1.svcl.client.entityset.CustomerEquipmentCards;
import com.sap.b1.svcl.client.entityset.CustomsDeclaration;
import com.sap.b1.svcl.client.entityset.CustomsGroups;
import com.sap.b1.svcl.client.entityset.CycleCountDeterminations;
import com.sap.b1.svcl.client.entityset.DNFCodeSetup;
import com.sap.b1.svcl.client.entityset.DeductionTaxGroups;
import com.sap.b1.svcl.client.entityset.DeductionTaxHierarchies;
import com.sap.b1.svcl.client.entityset.DeductionTaxSubGroups;
import com.sap.b1.svcl.client.entityset.DeliveryNotes;
import com.sap.b1.svcl.client.entityset.Departments;
import com.sap.b1.svcl.client.entityset.Deposits;
import com.sap.b1.svcl.client.entityset.DepreciationAreas;
import com.sap.b1.svcl.client.entityset.DepreciationTypePools;
import com.sap.b1.svcl.client.entityset.DepreciationTypes;
import com.sap.b1.svcl.client.entityset.DeterminationCriterias;
import com.sap.b1.svcl.client.entityset.Dimensions;
import com.sap.b1.svcl.client.entityset.DistributionRules;
import com.sap.b1.svcl.client.entityset.DownPayments;
import com.sap.b1.svcl.client.entityset.Drafts;
import com.sap.b1.svcl.client.entityset.DunningLetters;
import com.sap.b1.svcl.client.entityset.DunningTerms;
import com.sap.b1.svcl.client.entityset.DynamicSystemStrings;
import com.sap.b1.svcl.client.entityset.ElectronicFileFormats;
import com.sap.b1.svcl.client.entityset.EmailGroups;
import com.sap.b1.svcl.client.entityset.EmployeeIDType;
import com.sap.b1.svcl.client.entityset.EmployeePosition;
import com.sap.b1.svcl.client.entityset.EmployeeRolesSetup;
import com.sap.b1.svcl.client.entityset.EmployeeStatus;
import com.sap.b1.svcl.client.entityset.EmployeeTransfers;
import com.sap.b1.svcl.client.entityset.EmployeesInfo;
import com.sap.b1.svcl.client.entityset.EnhancedDiscountGroups;
import com.sap.b1.svcl.client.entityset.ExpenseTypes;
import com.sap.b1.svcl.client.entityset.ExtendedTranslations;
import com.sap.b1.svcl.client.entityset.FAAccountDeterminations;
import com.sap.b1.svcl.client.entityset.FactoringIndicators;
import com.sap.b1.svcl.client.entityset.FinancialYears;
import com.sap.b1.svcl.client.entityset.FiscalPrinter;
import com.sap.b1.svcl.client.entityset.FormPreferences;
import com.sap.b1.svcl.client.entityset.FormattedSearches;
import com.sap.b1.svcl.client.entityset.Forms1099;
import com.sap.b1.svcl.client.entityset.GLAccountAdvancedRules;
import com.sap.b1.svcl.client.entityset.GoodsReturnRequest;
import com.sap.b1.svcl.client.entityset.GovPayCodes;
import com.sap.b1.svcl.client.entityset.HouseBankAccounts;
import com.sap.b1.svcl.client.entityset.IncomingPayments;
import com.sap.b1.svcl.client.entityset.IndiaSacCode;
import com.sap.b1.svcl.client.entityset.Industries;
import com.sap.b1.svcl.client.entityset.IntegrationPackagesConfigure;
import com.sap.b1.svcl.client.entityset.InternalReconciliations;
import com.sap.b1.svcl.client.entityset.IntrastatConfiguration;
import com.sap.b1.svcl.client.entityset.InventoryCountings;
import com.sap.b1.svcl.client.entityset.InventoryCycles;
import com.sap.b1.svcl.client.entityset.InventoryGenEntries;
import com.sap.b1.svcl.client.entityset.InventoryGenExits;
import com.sap.b1.svcl.client.entityset.InventoryOpeningBalances;
import com.sap.b1.svcl.client.entityset.InventoryPostings;
import com.sap.b1.svcl.client.entityset.InventoryTransferRequests;
import com.sap.b1.svcl.client.entityset.Invoices;
import com.sap.b1.svcl.client.entityset.ItemGroups;
import com.sap.b1.svcl.client.entityset.ItemImages;
import com.sap.b1.svcl.client.entityset.ItemProperties;
import com.sap.b1.svcl.client.entityset.Items;
import com.sap.b1.svcl.client.entityset.JournalEntries;
import com.sap.b1.svcl.client.entityset.JournalEntryDocumentTypes;
import com.sap.b1.svcl.client.entityset.KPIs;
import com.sap.b1.svcl.client.entityset.KnowledgeBaseSolutions;
import com.sap.b1.svcl.client.entityset.LandedCosts;
import com.sap.b1.svcl.client.entityset.LandedCostsCodes;
import com.sap.b1.svcl.client.entityset.LegalData;
import com.sap.b1.svcl.client.entityset.LengthMeasures;
import com.sap.b1.svcl.client.entityset.LocalEra;
import com.sap.b1.svcl.client.entityset.Manufacturers;
import com.sap.b1.svcl.client.entityset.MaterialGroups;
import com.sap.b1.svcl.client.entityset.MaterialRevaluation;
import com.sap.b1.svcl.client.entityset.Messages;
import com.sap.b1.svcl.client.entityset.MobileAddOnSetting;
import com.sap.b1.svcl.client.entityset.MultiLanguageTranslations;
import com.sap.b1.svcl.client.entityset.NCMCodesSetup;
import com.sap.b1.svcl.client.entityset.NFModels;
import com.sap.b1.svcl.client.entityset.NFTaxCategories;
import com.sap.b1.svcl.client.entityset.NatureOfAssessees;
import com.sap.b1.svcl.client.entityset.OccurrenceCodes;
import com.sap.b1.svcl.client.entityset.Orders;
import com.sap.b1.svcl.client.entityset.POSDailySummary;
import com.sap.b1.svcl.client.entityset.PackagesTypes;
import com.sap.b1.svcl.client.entityset.PartnersSetups;
import com.sap.b1.svcl.client.entityset.PaymentBlocks;
import com.sap.b1.svcl.client.entityset.PaymentDrafts;
import com.sap.b1.svcl.client.entityset.PaymentRunExport;
import com.sap.b1.svcl.client.entityset.PaymentTermsTypes;
import com.sap.b1.svcl.client.entityset.PickLists;
import com.sap.b1.svcl.client.entityset.PredefinedTexts;
import com.sap.b1.svcl.client.entityset.PriceLists;
import com.sap.b1.svcl.client.entityset.ProductTrees;
import com.sap.b1.svcl.client.entityset.ProductionOrders;
import com.sap.b1.svcl.client.entityset.ProfitCenters;
import com.sap.b1.svcl.client.entityset.ProjectManagementTimeSheet;
import com.sap.b1.svcl.client.entityset.ProjectManagements;
import com.sap.b1.svcl.client.entityset.Projects;
import com.sap.b1.svcl.client.entityset.PurchaseCreditNotes;
import com.sap.b1.svcl.client.entityset.PurchaseDeliveryNotes;
import com.sap.b1.svcl.client.entityset.PurchaseDownPayments;
import com.sap.b1.svcl.client.entityset.PurchaseInvoices;
import com.sap.b1.svcl.client.entityset.PurchaseOrders;
import com.sap.b1.svcl.client.entityset.PurchaseQuotations;
import com.sap.b1.svcl.client.entityset.PurchaseRequests;
import com.sap.b1.svcl.client.entityset.PurchaseReturns;
import com.sap.b1.svcl.client.entityset.PurchaseTaxInvoices;
import com.sap.b1.svcl.client.entityset.QueryAuthGroups;
import com.sap.b1.svcl.client.entityset.QueryCategories;
import com.sap.b1.svcl.client.entityset.Queue;
import com.sap.b1.svcl.client.entityset.Quotations;
import com.sap.b1.svcl.client.entityset.Relationships;
import com.sap.b1.svcl.client.entityset.ReportFilter;
import com.sap.b1.svcl.client.entityset.ReportTypes;
import com.sap.b1.svcl.client.entityset.ResourceCapacities;
import com.sap.b1.svcl.client.entityset.ResourceGroups;
import com.sap.b1.svcl.client.entityset.ResourceProperties;
import com.sap.b1.svcl.client.entityset.Resources;
import com.sap.b1.svcl.client.entityset.RetornoCodes;
import com.sap.b1.svcl.client.entityset.ReturnRequest;
import com.sap.b1.svcl.client.entityset.Returns;
import com.sap.b1.svcl.client.entityset.RouteStages;
import com.sap.b1.svcl.client.entityset.SalesForecast;
import com.sap.b1.svcl.client.entityset.SalesOpportunities;
import com.sap.b1.svcl.client.entityset.SalesOpportunityCompetitorsSetup;
import com.sap.b1.svcl.client.entityset.SalesOpportunityInterestsSetup;
import com.sap.b1.svcl.client.entityset.SalesOpportunityReasonsSetup;
import com.sap.b1.svcl.client.entityset.SalesOpportunitySourcesSetup;
import com.sap.b1.svcl.client.entityset.SalesPersons;
import com.sap.b1.svcl.client.entityset.SalesStages;
import com.sap.b1.svcl.client.entityset.SalesTaxAuthorities;
import com.sap.b1.svcl.client.entityset.SalesTaxAuthoritiesTypes;
import com.sap.b1.svcl.client.entityset.SalesTaxCodes;
import com.sap.b1.svcl.client.entityset.SalesTaxInvoices;
import com.sap.b1.svcl.client.entityset.Sections;
import com.sap.b1.svcl.client.entityset.SerialNumberDetails;
import com.sap.b1.svcl.client.entityset.ServiceCallOrigins;
import com.sap.b1.svcl.client.entityset.ServiceCallProblemSubTypes;
import com.sap.b1.svcl.client.entityset.ServiceCallProblemTypes;
import com.sap.b1.svcl.client.entityset.ServiceCallSolutionStatus;
import com.sap.b1.svcl.client.entityset.ServiceCallStatus;
import com.sap.b1.svcl.client.entityset.ServiceCallTypes;
import com.sap.b1.svcl.client.entityset.ServiceCalls;
import com.sap.b1.svcl.client.entityset.ServiceContracts;
import com.sap.b1.svcl.client.entityset.ServiceGroups;
import com.sap.b1.svcl.client.entityset.ShippingTypes;
import com.sap.b1.svcl.client.entityset.SpecialPrices;
import com.sap.b1.svcl.client.entityset.States;
import com.sap.b1.svcl.client.entityset.StockTakings;
import com.sap.b1.svcl.client.entityset.StockTransferDrafts;
import com.sap.b1.svcl.client.entityset.StockTransfers;
import com.sap.b1.svcl.client.entityset.TargetGroups;
import com.sap.b1.svcl.client.entityset.TaxCodeDeterminations;
import com.sap.b1.svcl.client.entityset.TaxCodeDeterminationsTCD;
import com.sap.b1.svcl.client.entityset.TaxInvoiceReport;
import com.sap.b1.svcl.client.entityset.TaxWebSites;
import com.sap.b1.svcl.client.entityset.Teams;
import com.sap.b1.svcl.client.entityset.TerminationReason;
import com.sap.b1.svcl.client.entityset.Territories;
import com.sap.b1.svcl.client.entityset.TrackingNotes;
import com.sap.b1.svcl.client.entityset.TransactionCodes;
import com.sap.b1.svcl.client.entityset.TransportationDocument;
import com.sap.b1.svcl.client.entityset.UnitOfMeasurementGroups;
import com.sap.b1.svcl.client.entityset.UnitOfMeasurements;
import com.sap.b1.svcl.client.entityset.UserDefaultGroups;
import com.sap.b1.svcl.client.entityset.UserFieldsMD;
import com.sap.b1.svcl.client.entityset.UserGroups;
import com.sap.b1.svcl.client.entityset.UserKeysMD;
import com.sap.b1.svcl.client.entityset.UserLanguages;
import com.sap.b1.svcl.client.entityset.UserObjectsMD;
import com.sap.b1.svcl.client.entityset.UserPermissionTree;
import com.sap.b1.svcl.client.entityset.UserQueries;
import com.sap.b1.svcl.client.entityset.UserTablesMD;
import com.sap.b1.svcl.client.entityset.Users;
import com.sap.b1.svcl.client.entityset.ValueMapping;
import com.sap.b1.svcl.client.entityset.ValueMappingCommunication;
import com.sap.b1.svcl.client.entityset.VatGroups;
import com.sap.b1.svcl.client.entityset.VendorPayments;
import com.sap.b1.svcl.client.entityset.WarehouseLocations;
import com.sap.b1.svcl.client.entityset.WarehouseSublevelCodes;
import com.sap.b1.svcl.client.entityset.Warehouses;
import com.sap.b1.svcl.client.entityset.WebClientBookmarkTiles;
import com.sap.b1.svcl.client.entityset.WebClientDashboards;
import com.sap.b1.svcl.client.entityset.WebClientFormSettings;
import com.sap.b1.svcl.client.entityset.WebClientLaunchpads;
import com.sap.b1.svcl.client.entityset.WebClientListviewFilters;
import com.sap.b1.svcl.client.entityset.WebClientNotifications;
import com.sap.b1.svcl.client.entityset.WebClientPreferences;
import com.sap.b1.svcl.client.entityset.WebClientRecentActivities;
import com.sap.b1.svcl.client.entityset.WebClientVariantGroups;
import com.sap.b1.svcl.client.entityset.WebClientVariants;
import com.sap.b1.svcl.client.entityset.WeightMeasures;
import com.sap.b1.svcl.client.entityset.WithholdingTaxCodes;
import com.sap.b1.svcl.client.entityset.WitholdingTaxDefinition;
import com.sap.b1.svcl.client.entityset.WizardPaymentMethods;
import com.sap.b1.svcl.client.functionimport.AccountCategoryService;
import com.sap.b1.svcl.client.functionimport.AccountsService;
import com.sap.b1.svcl.client.functionimport.AccrualTypesService;
import com.sap.b1.svcl.client.functionimport.ActivitiesService;
import com.sap.b1.svcl.client.functionimport.ActivityRecipientListsService;
import com.sap.b1.svcl.client.functionimport.AlternativeItemsService;
import com.sap.b1.svcl.client.functionimport.ApprovalRequestsService;
import com.sap.b1.svcl.client.functionimport.ApprovalStagesService;
import com.sap.b1.svcl.client.functionimport.ApprovalTemplatesService;
import com.sap.b1.svcl.client.functionimport.AssetCapitalizationCreditMemoService;
import com.sap.b1.svcl.client.functionimport.AssetCapitalizationService;
import com.sap.b1.svcl.client.functionimport.AssetClassesService;
import com.sap.b1.svcl.client.functionimport.AssetDepreciationGroupsService;
import com.sap.b1.svcl.client.functionimport.AssetGroupsService;
import com.sap.b1.svcl.client.functionimport.AssetManualDepreciationService;
import com.sap.b1.svcl.client.functionimport.AssetRetirementService;
import com.sap.b1.svcl.client.functionimport.AssetTransferService;
import com.sap.b1.svcl.client.functionimport.AttributeGroupsService;
import com.sap.b1.svcl.client.functionimport.BOEDocumentTypesService;
import com.sap.b1.svcl.client.functionimport.BOEInstructionsService;
import com.sap.b1.svcl.client.functionimport.BOELinesService;
import com.sap.b1.svcl.client.functionimport.BOEPortfoliosService;
import com.sap.b1.svcl.client.functionimport.BPOpeningBalanceService;
import com.sap.b1.svcl.client.functionimport.BankChargesAllocationCodesService;
import com.sap.b1.svcl.client.functionimport.BankStatementsService;
import com.sap.b1.svcl.client.functionimport.BarCodesService;
import com.sap.b1.svcl.client.functionimport.BinLocationAttributesService;
import com.sap.b1.svcl.client.functionimport.BinLocationFieldsService;
import com.sap.b1.svcl.client.functionimport.BinLocationsService;
import com.sap.b1.svcl.client.functionimport.BlanketAgreementsService;
import com.sap.b1.svcl.client.functionimport.BranchesService;
import com.sap.b1.svcl.client.functionimport.BrazilBeverageIndexersService;
import com.sap.b1.svcl.client.functionimport.BrazilFuelIndexersService;
import com.sap.b1.svcl.client.functionimport.BusinessPartnerPropertiesService;
import com.sap.b1.svcl.client.functionimport.BusinessPartnersService;
import com.sap.b1.svcl.client.functionimport.CampaignResponseTypeService;
import com.sap.b1.svcl.client.functionimport.CampaignsService;
import com.sap.b1.svcl.client.functionimport.CashDiscountsService;
import com.sap.b1.svcl.client.functionimport.CashFlowLineItemsService;
import com.sap.b1.svcl.client.functionimport.CertificateSeriesService;
import com.sap.b1.svcl.client.functionimport.ChangeLogsService;
import com.sap.b1.svcl.client.functionimport.CheckLinesService;
import com.sap.b1.svcl.client.functionimport.CockpitsService;
import com.sap.b1.svcl.client.functionimport.CompanyService;
import com.sap.b1.svcl.client.functionimport.CorrectionInvoiceReversalService;
import com.sap.b1.svcl.client.functionimport.CorrectionInvoiceService;
import com.sap.b1.svcl.client.functionimport.CorrectionPurchaseInvoiceReversalService;
import com.sap.b1.svcl.client.functionimport.CorrectionPurchaseInvoiceService;
import com.sap.b1.svcl.client.functionimport.CostCenterTypesService;
import com.sap.b1.svcl.client.functionimport.CostElementService;
import com.sap.b1.svcl.client.functionimport.CountriesService;
import com.sap.b1.svcl.client.functionimport.CreditLinesService;
import com.sap.b1.svcl.client.functionimport.CreditNotesService;
import com.sap.b1.svcl.client.functionimport.CycleCountDeterminationsService;
import com.sap.b1.svcl.client.functionimport.DNFCodeSetupService;
import com.sap.b1.svcl.client.functionimport.DashboardPackagesService;
import com.sap.b1.svcl.client.functionimport.DeductionTaxSubGroupsService;
import com.sap.b1.svcl.client.functionimport.DeliveryNotesService;
import com.sap.b1.svcl.client.functionimport.DepartmentsService;
import com.sap.b1.svcl.client.functionimport.DepositsService;
import com.sap.b1.svcl.client.functionimport.DepreciationAreasService;
import com.sap.b1.svcl.client.functionimport.DepreciationTypePoolsService;
import com.sap.b1.svcl.client.functionimport.DepreciationTypesService;
import com.sap.b1.svcl.client.functionimport.DeterminationCriteriasService;
import com.sap.b1.svcl.client.functionimport.DimensionsService;
import com.sap.b1.svcl.client.functionimport.DistributionRulesService;
import com.sap.b1.svcl.client.functionimport.DownPaymentsService;
import com.sap.b1.svcl.client.functionimport.DraftsService;
import com.sap.b1.svcl.client.functionimport.DunningTermsService;
import com.sap.b1.svcl.client.functionimport.ElectronicCommunicationActionService;
import com.sap.b1.svcl.client.functionimport.ElectronicCommunicationActionsService;
import com.sap.b1.svcl.client.functionimport.ElectronicFileFormatsService;
import com.sap.b1.svcl.client.functionimport.EmailGroupsService;
import com.sap.b1.svcl.client.functionimport.EmployeeIDTypeService;
import com.sap.b1.svcl.client.functionimport.EmployeePositionService;
import com.sap.b1.svcl.client.functionimport.EmployeeRolesSetupService;
import com.sap.b1.svcl.client.functionimport.EmployeeStatusService;
import com.sap.b1.svcl.client.functionimport.EmployeeTransfersService;
import com.sap.b1.svcl.client.functionimport.EnhancedDiscountGroupsService;
import com.sap.b1.svcl.client.functionimport.ExtendedTranslationsService;
import com.sap.b1.svcl.client.functionimport.ExternalCallsService;
import com.sap.b1.svcl.client.functionimport.ExternalReconciliationsService;
import com.sap.b1.svcl.client.functionimport.FAAccountDeterminationsService;
import com.sap.b1.svcl.client.functionimport.FinancialYearsService;
import com.sap.b1.svcl.client.functionimport.FiscalPrinterService;
import com.sap.b1.svcl.client.functionimport.FixedAssetItemsService;
import com.sap.b1.svcl.client.functionimport.GLAccountAdvancedRulesService;
import com.sap.b1.svcl.client.functionimport.GTIsService;
import com.sap.b1.svcl.client.functionimport.GoodsReturnRequestService;
import com.sap.b1.svcl.client.functionimport.GovPayCodesService;
import com.sap.b1.svcl.client.functionimport.IndiaSacCodeService;
import com.sap.b1.svcl.client.functionimport.IntegrationPackagesConfigureService;
import com.sap.b1.svcl.client.functionimport.InternalReconciliationsService;
import com.sap.b1.svcl.client.functionimport.IntrastatConfigurationService;
import com.sap.b1.svcl.client.functionimport.InventoryCountingsService;
import com.sap.b1.svcl.client.functionimport.InventoryGenEntryService;
import com.sap.b1.svcl.client.functionimport.InventoryGenExitService;
import com.sap.b1.svcl.client.functionimport.InventoryOpeningBalancesService;
import com.sap.b1.svcl.client.functionimport.InventoryPostingsService;
import com.sap.b1.svcl.client.functionimport.InventoryTransferRequestsService;
import com.sap.b1.svcl.client.functionimport.InvoicesService;
import com.sap.b1.svcl.client.functionimport.JournalEntryDocumentTypeService;
import com.sap.b1.svcl.client.functionimport.JournalVouchersService;
import com.sap.b1.svcl.client.functionimport.KPIsService;
import com.sap.b1.svcl.client.functionimport.LandedCostsService;
import com.sap.b1.svcl.client.functionimport.LicenseService;
import com.sap.b1.svcl.client.functionimport.Login;
import com.sap.b1.svcl.client.functionimport.Logout;
import com.sap.b1.svcl.client.functionimport.MaterialGroupsService;
import com.sap.b1.svcl.client.functionimport.MaterialRevaluationFIFOService;
import com.sap.b1.svcl.client.functionimport.MaterialRevaluationSNBService;
import com.sap.b1.svcl.client.functionimport.MessagesService;
import com.sap.b1.svcl.client.functionimport.MobileAddOnSettingService;
import com.sap.b1.svcl.client.functionimport.MobileAppService;
import com.sap.b1.svcl.client.functionimport.NCMCodesSetupService;
import com.sap.b1.svcl.client.functionimport.NFModelsService;
import com.sap.b1.svcl.client.functionimport.NFTaxCategoriesService;
import com.sap.b1.svcl.client.functionimport.NatureOfAssesseesService;
import com.sap.b1.svcl.client.functionimport.OccurrenceCodesService;
import com.sap.b1.svcl.client.functionimport.OrdersService;
import com.sap.b1.svcl.client.functionimport.PartnersSetupsService;
import com.sap.b1.svcl.client.functionimport.PaymentBlocksService;
import com.sap.b1.svcl.client.functionimport.PaymentCalculationService;
import com.sap.b1.svcl.client.functionimport.PaymentTermsTypesService;
import com.sap.b1.svcl.client.functionimport.PickListsService;
import com.sap.b1.svcl.client.functionimport.PredefinedTextsService;
import com.sap.b1.svcl.client.functionimport.ProfitCentersService;
import com.sap.b1.svcl.client.functionimport.ProjectManagementConfigurationService;
import com.sap.b1.svcl.client.functionimport.ProjectManagementService;
import com.sap.b1.svcl.client.functionimport.ProjectsService;
import com.sap.b1.svcl.client.functionimport.PurchaseCreditNotesService;
import com.sap.b1.svcl.client.functionimport.PurchaseDeliveryNotesService;
import com.sap.b1.svcl.client.functionimport.PurchaseDownPaymentsService;
import com.sap.b1.svcl.client.functionimport.PurchaseInvoicesService;
import com.sap.b1.svcl.client.functionimport.PurchaseOrdersService;
import com.sap.b1.svcl.client.functionimport.PurchaseQuotationsService;
import com.sap.b1.svcl.client.functionimport.PurchaseRequestService;
import com.sap.b1.svcl.client.functionimport.PurchaseReturnsService;
import com.sap.b1.svcl.client.functionimport.QueryAuthGroupService;
import com.sap.b1.svcl.client.functionimport.QueryService;
import com.sap.b1.svcl.client.functionimport.QuotationsService;
import com.sap.b1.svcl.client.functionimport.RecurringTransactionService;
import com.sap.b1.svcl.client.functionimport.ReportFilterService;
import com.sap.b1.svcl.client.functionimport.ReportLayoutsService;
import com.sap.b1.svcl.client.functionimport.ReportTypesService;
import com.sap.b1.svcl.client.functionimport.ResourceCapacitiesService;
import com.sap.b1.svcl.client.functionimport.ResourceGroupsService;
import com.sap.b1.svcl.client.functionimport.ResourcePropertiesService;
import com.sap.b1.svcl.client.functionimport.ResourcesService;
import com.sap.b1.svcl.client.functionimport.RetornoCodesService;
import com.sap.b1.svcl.client.functionimport.ReturnRequestService;
import com.sap.b1.svcl.client.functionimport.ReturnsService;
import com.sap.b1.svcl.client.functionimport.RouteStagesService;
import com.sap.b1.svcl.client.functionimport.RoutingDateCalculationService;
import com.sap.b1.svcl.client.functionimport.SBOBobService;
import com.sap.b1.svcl.client.functionimport.SalesOpportunityCompetitorsSetupService;
import com.sap.b1.svcl.client.functionimport.SalesOpportunityInterestsSetupService;
import com.sap.b1.svcl.client.functionimport.SalesOpportunityReasonsSetupService;
import com.sap.b1.svcl.client.functionimport.SalesOpportunitySourcesSetupService;
import com.sap.b1.svcl.client.functionimport.SectionsService;
import com.sap.b1.svcl.client.functionimport.SensitiveDataAccessService;
import com.sap.b1.svcl.client.functionimport.SeriesService;
import com.sap.b1.svcl.client.functionimport.ServiceCallOriginsService;
import com.sap.b1.svcl.client.functionimport.ServiceCallProblemSubTypesService;
import com.sap.b1.svcl.client.functionimport.ServiceCallProblemTypesService;
import com.sap.b1.svcl.client.functionimport.ServiceCallSolutionStatusService;
import com.sap.b1.svcl.client.functionimport.ServiceCallStatusService;
import com.sap.b1.svcl.client.functionimport.ServiceCallTypesService;
import com.sap.b1.svcl.client.functionimport.ServiceGroupsService;
import com.sap.b1.svcl.client.functionimport.ServiceTaxPostingService;
import com.sap.b1.svcl.client.functionimport.StatesService;
import com.sap.b1.svcl.client.functionimport.StockTransferDraftService;
import com.sap.b1.svcl.client.functionimport.StockTransferService;
import com.sap.b1.svcl.client.functionimport.TargetGroupsService;
import com.sap.b1.svcl.client.functionimport.TaxCodeDeterminationsService;
import com.sap.b1.svcl.client.functionimport.TaxCodeDeterminationsTCDService;
import com.sap.b1.svcl.client.functionimport.TaxWebSitesService;
import com.sap.b1.svcl.client.functionimport.TerminationReasonService;
import com.sap.b1.svcl.client.functionimport.TrackingNotesService;
import com.sap.b1.svcl.client.functionimport.TransactionCodesService;
import com.sap.b1.svcl.client.functionimport.UnitOfMeasurementGroupsService;
import com.sap.b1.svcl.client.functionimport.UnitOfMeasurementsService;
import com.sap.b1.svcl.client.functionimport.UserGroupService;
import com.sap.b1.svcl.client.functionimport.UserMenuService;
import com.sap.b1.svcl.client.functionimport.ValueMappingService;
import com.sap.b1.svcl.client.functionimport.WarehouseSublevelCodesService;
import com.sap.b1.svcl.client.functionimport.WebClientBookmarkTileService;
import com.sap.b1.svcl.client.functionimport.WebClientDashboardService;
import com.sap.b1.svcl.client.functionimport.WebClientFormSettingService;
import com.sap.b1.svcl.client.functionimport.WebClientLaunchpadService;
import com.sap.b1.svcl.client.functionimport.WebClientListviewFilterService;
import com.sap.b1.svcl.client.functionimport.WebClientNotificationService;
import com.sap.b1.svcl.client.functionimport.WebClientPreferenceService;
import com.sap.b1.svcl.client.functionimport.WebClientRecentActivityService;
import com.sap.b1.svcl.client.functionimport.WebClientVariantGroupService;
import com.sap.b1.svcl.client.functionimport.WebClientVariantService;
import com.sap.b1.svcl.client.functionimport.WorkflowTaskService;
import com.sap.b1.util.HttpClientPoolUtil;
import feign.Client;
import feign.Feign;
import feign.Logger;
import feign.RequestInterceptor;
import feign.hc5.ApacheHttp5Client;
import feign.slf4j.Slf4jLogger;
import java.io.IOException;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SvclConfig {
    ServiceLayerClient client;
    @Value(value="${b1_svcl_url}")
    String svclUrl;

    @Autowired
    public void init() throws Exception {
        CloseableHttpClient httpClients = HttpClientPoolUtil.buildSDKClient();
        Feign.Builder builder = Feign.builder().client((Client)new ApacheHttp5Client((HttpClient)httpClients)).logger((Logger)new Slf4jLogger()).logLevel(Logger.Level.FULL);
        builder.requestInterceptor((RequestInterceptor)new SvclRequestInterceptor());
        if (httpClients != null) {
            try {
                httpClients.close();
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        this.client = new ServiceLayerClient(builder, this.svclUrl + "/b1s/v1");
    }

    @Bean
    AccountCategory initAccountCategory() {
        return (AccountCategory)this.client.target(AccountCategory.class);
    }

    @Bean
    AccountSegmentationCategories initAccountSegmentationCategories() {
        return (AccountSegmentationCategories)this.client.target(AccountSegmentationCategories.class);
    }

    @Bean
    AccountSegmentations initAccountSegmentations() {
        return (AccountSegmentations)this.client.target(AccountSegmentations.class);
    }

    @Bean
    AccrualTypes initAccrualTypes() {
        return (AccrualTypes)this.client.target(AccrualTypes.class);
    }

    @Bean
    Activities initActivities() {
        return (Activities)this.client.target(Activities.class);
    }

    @Bean
    ActivityLocations initActivityLocations() {
        return (ActivityLocations)this.client.target(ActivityLocations.class);
    }

    @Bean
    ActivityRecipientLists initActivityRecipientLists() {
        return (ActivityRecipientLists)this.client.target(ActivityRecipientLists.class);
    }

    @Bean
    ActivityStatuses initActivityStatuses() {
        return (ActivityStatuses)this.client.target(ActivityStatuses.class);
    }

    @Bean
    ActivityTypes initActivityTypes() {
        return (ActivityTypes)this.client.target(ActivityTypes.class);
    }

    @Bean
    AdditionalExpenses initAdditionalExpenses() {
        return (AdditionalExpenses)this.client.target(AdditionalExpenses.class);
    }

    @Bean
    AlertManagements initAlertManagements() {
        return (AlertManagements)this.client.target(AlertManagements.class);
    }

    @Bean
    AlternateCatNum initAlternateCatNum() {
        return (AlternateCatNum)this.client.target(AlternateCatNum.class);
    }

    @Bean
    ApprovalRequests initApprovalRequests() {
        return (ApprovalRequests)this.client.target(ApprovalRequests.class);
    }

    @Bean
    ApprovalStages initApprovalStages() {
        return (ApprovalStages)this.client.target(ApprovalStages.class);
    }

    @Bean
    ApprovalTemplates initApprovalTemplates() {
        return (ApprovalTemplates)this.client.target(ApprovalTemplates.class);
    }

    @Bean
    AssetCapitalization initAssetCapitalization() {
        return (AssetCapitalization)this.client.target(AssetCapitalization.class);
    }

    @Bean
    AssetCapitalizationCreditMemo initAssetCapitalizationCreditMemo() {
        return (AssetCapitalizationCreditMemo)this.client.target(AssetCapitalizationCreditMemo.class);
    }

    @Bean
    AssetClasses initAssetClasses() {
        return (AssetClasses)this.client.target(AssetClasses.class);
    }

    @Bean
    AssetDepreciationGroups initAssetDepreciationGroups() {
        return (AssetDepreciationGroups)this.client.target(AssetDepreciationGroups.class);
    }

    @Bean
    AssetGroups initAssetGroups() {
        return (AssetGroups)this.client.target(AssetGroups.class);
    }

    @Bean
    AssetManualDepreciation initAssetManualDepreciation() {
        return (AssetManualDepreciation)this.client.target(AssetManualDepreciation.class);
    }

    @Bean
    AssetRetirement initAssetRetirement() {
        return (AssetRetirement)this.client.target(AssetRetirement.class);
    }

    @Bean
    AssetTransfer initAssetTransfer() {
        return (AssetTransfer)this.client.target(AssetTransfer.class);
    }

    @Bean
    Attachments2 initAttachments2() {
        return (Attachments2)this.client.target(Attachments2.class);
    }

    @Bean
    AttributeGroups initAttributeGroups() {
        return (AttributeGroups)this.client.target(AttributeGroups.class);
    }

    @Bean
    B1Sessions initB1Sessions() {
        return (B1Sessions)this.client.target(B1Sessions.class);
    }

    @Bean
    BankChargesAllocationCodes initBankChargesAllocationCodes() {
        return (BankChargesAllocationCodes)this.client.target(BankChargesAllocationCodes.class);
    }

    @Bean
    BankPages initBankPages() {
        return (BankPages)this.client.target(BankPages.class);
    }

    @Bean
    Banks initBanks() {
        return (Banks)this.client.target(Banks.class);
    }

    @Bean
    BankStatements initBankStatements() {
        return (BankStatements)this.client.target(BankStatements.class);
    }

    @Bean
    BarCodes initBarCodes() {
        return (BarCodes)this.client.target(BarCodes.class);
    }

    @Bean
    BatchNumberDetails initBatchNumberDetails() {
        return (BatchNumberDetails)this.client.target(BatchNumberDetails.class);
    }

    @Bean
    BillOfExchangeTransactions initBillOfExchangeTransactions() {
        return (BillOfExchangeTransactions)this.client.target(BillOfExchangeTransactions.class);
    }

    @Bean
    BinLocationAttributes initBinLocationAttributes() {
        return (BinLocationAttributes)this.client.target(BinLocationAttributes.class);
    }

    @Bean
    BinLocationFields initBinLocationFields() {
        return (BinLocationFields)this.client.target(BinLocationFields.class);
    }

    @Bean
    BinLocations initBinLocations() {
        return (BinLocations)this.client.target(BinLocations.class);
    }

    @Bean
    BlanketAgreements initBlanketAgreements() {
        return (BlanketAgreements)this.client.target(BlanketAgreements.class);
    }

    @Bean
    BOEDocumentTypes initBOEDocumentTypes() {
        return (BOEDocumentTypes)this.client.target(BOEDocumentTypes.class);
    }

    @Bean
    BOEInstructions initBOEInstructions() {
        return (BOEInstructions)this.client.target(BOEInstructions.class);
    }

    @Bean
    BOEPortfolios initBOEPortfolios() {
        return (BOEPortfolios)this.client.target(BOEPortfolios.class);
    }

    @Bean
    BPPriorities initBPPriorities() {
        return (BPPriorities)this.client.target(BPPriorities.class);
    }

    @Bean
    Branches initBranches() {
        return (Branches)this.client.target(Branches.class);
    }

    @Bean
    BrazilBeverageIndexers initBrazilBeverageIndexers() {
        return (BrazilBeverageIndexers)this.client.target(BrazilBeverageIndexers.class);
    }

    @Bean
    BrazilFuelIndexers initBrazilFuelIndexers() {
        return (BrazilFuelIndexers)this.client.target(BrazilFuelIndexers.class);
    }

    @Bean
    BrazilMultiIndexers initBrazilMultiIndexers() {
        return (BrazilMultiIndexers)this.client.target(BrazilMultiIndexers.class);
    }

    @Bean
    BrazilNumericIndexers initBrazilNumericIndexers() {
        return (BrazilNumericIndexers)this.client.target(BrazilNumericIndexers.class);
    }

    @Bean
    BrazilStringIndexers initBrazilStringIndexers() {
        return (BrazilStringIndexers)this.client.target(BrazilStringIndexers.class);
    }

    @Bean
    BudgetDistributions initBudgetDistributions() {
        return (BudgetDistributions)this.client.target(BudgetDistributions.class);
    }

    @Bean
    Budgets initBudgets() {
        return (Budgets)this.client.target(Budgets.class);
    }

    @Bean
    BudgetScenarios initBudgetScenarios() {
        return (BudgetScenarios)this.client.target(BudgetScenarios.class);
    }

    @Bean
    BusinessPartnerGroups initBusinessPartnerGroups() {
        return (BusinessPartnerGroups)this.client.target(BusinessPartnerGroups.class);
    }

    @Bean
    BusinessPartnerProperties initBusinessPartnerProperties() {
        return (BusinessPartnerProperties)this.client.target(BusinessPartnerProperties.class);
    }

    @Bean
    BusinessPartners initBusinessPartners() {
        return (BusinessPartners)this.client.target(BusinessPartners.class);
    }

    @Bean
    BusinessPlaces initBusinessPlaces() {
        return (BusinessPlaces)this.client.target(BusinessPlaces.class);
    }

    @Bean
    CampaignResponseType initCampaignResponseType() {
        return (CampaignResponseType)this.client.target(CampaignResponseType.class);
    }

    @Bean
    Campaigns initCampaigns() {
        return (Campaigns)this.client.target(Campaigns.class);
    }

    @Bean
    CashDiscounts initCashDiscounts() {
        return (CashDiscounts)this.client.target(CashDiscounts.class);
    }

    @Bean
    CashFlowLineItems initCashFlowLineItems() {
        return (CashFlowLineItems)this.client.target(CashFlowLineItems.class);
    }

    @Bean
    CertificateSeries initCertificateSeries() {
        return (CertificateSeries)this.client.target(CertificateSeries.class);
    }

    @Bean
    ChartOfAccounts initChartOfAccounts() {
        return (ChartOfAccounts)this.client.target(ChartOfAccounts.class);
    }

    @Bean
    ChecksforPayment initChecksforPayment() {
        return (ChecksforPayment)this.client.target(ChecksforPayment.class);
    }

    @Bean
    ChooseFromList initChooseFromList() {
        return (ChooseFromList)this.client.target(ChooseFromList.class);
    }

    @Bean
    Cockpits initCockpits() {
        return (Cockpits)this.client.target(Cockpits.class);
    }

    @Bean
    CommissionGroups initCommissionGroups() {
        return (CommissionGroups)this.client.target(CommissionGroups.class);
    }

    @Bean
    Contacts initContacts() {
        return (Contacts)this.client.target(Contacts.class);
    }

    @Bean
    ContractTemplates initContractTemplates() {
        return (ContractTemplates)this.client.target(ContractTemplates.class);
    }

    @Bean
    CorrectionInvoice initCorrectionInvoice() {
        return (CorrectionInvoice)this.client.target(CorrectionInvoice.class);
    }

    @Bean
    CorrectionInvoiceReversal initCorrectionInvoiceReversal() {
        return (CorrectionInvoiceReversal)this.client.target(CorrectionInvoiceReversal.class);
    }

    @Bean
    CorrectionPurchaseInvoice initCorrectionPurchaseInvoice() {
        return (CorrectionPurchaseInvoice)this.client.target(CorrectionPurchaseInvoice.class);
    }

    @Bean
    CorrectionPurchaseInvoiceReversal initCorrectionPurchaseInvoiceReversal() {
        return (CorrectionPurchaseInvoiceReversal)this.client.target(CorrectionPurchaseInvoiceReversal.class);
    }

    @Bean
    CostCenterTypes initCostCenterTypes() {
        return (CostCenterTypes)this.client.target(CostCenterTypes.class);
    }

    @Bean
    CostElements initCostElements() {
        return (CostElements)this.client.target(CostElements.class);
    }

    @Bean
    Countries initCountries() {
        return (Countries)this.client.target(Countries.class);
    }

    @Bean
    CreditCardPayments initCreditCardPayments() {
        return (CreditCardPayments)this.client.target(CreditCardPayments.class);
    }

    @Bean
    CreditCards initCreditCards() {
        return (CreditCards)this.client.target(CreditCards.class);
    }

    @Bean
    CreditNotes initCreditNotes() {
        return (CreditNotes)this.client.target(CreditNotes.class);
    }

    @Bean
    CreditPaymentMethods initCreditPaymentMethods() {
        return (CreditPaymentMethods)this.client.target(CreditPaymentMethods.class);
    }

    @Bean
    Currencies initCurrencies() {
        return (Currencies)this.client.target(Currencies.class);
    }

    @Bean
    CustomerEquipmentCards initCustomerEquipmentCards() {
        return (CustomerEquipmentCards)this.client.target(CustomerEquipmentCards.class);
    }

    @Bean
    CustomsDeclaration initCustomsDeclaration() {
        return (CustomsDeclaration)this.client.target(CustomsDeclaration.class);
    }

    @Bean
    CustomsGroups initCustomsGroups() {
        return (CustomsGroups)this.client.target(CustomsGroups.class);
    }

    @Bean
    CycleCountDeterminations initCycleCountDeterminations() {
        return (CycleCountDeterminations)this.client.target(CycleCountDeterminations.class);
    }

    @Bean
    DeductionTaxGroups initDeductionTaxGroups() {
        return (DeductionTaxGroups)this.client.target(DeductionTaxGroups.class);
    }

    @Bean
    DeductionTaxHierarchies initDeductionTaxHierarchies() {
        return (DeductionTaxHierarchies)this.client.target(DeductionTaxHierarchies.class);
    }

    @Bean
    DeductionTaxSubGroups initDeductionTaxSubGroups() {
        return (DeductionTaxSubGroups)this.client.target(DeductionTaxSubGroups.class);
    }

    @Bean
    DeliveryNotes initDeliveryNotes() {
        return (DeliveryNotes)this.client.target(DeliveryNotes.class);
    }

    @Bean
    Departments initDepartments() {
        return (Departments)this.client.target(Departments.class);
    }

    @Bean
    Deposits initDeposits() {
        return (Deposits)this.client.target(Deposits.class);
    }

    @Bean
    DepreciationAreas initDepreciationAreas() {
        return (DepreciationAreas)this.client.target(DepreciationAreas.class);
    }

    @Bean
    DepreciationTypePools initDepreciationTypePools() {
        return (DepreciationTypePools)this.client.target(DepreciationTypePools.class);
    }

    @Bean
    DepreciationTypes initDepreciationTypes() {
        return (DepreciationTypes)this.client.target(DepreciationTypes.class);
    }

    @Bean
    DeterminationCriterias initDeterminationCriterias() {
        return (DeterminationCriterias)this.client.target(DeterminationCriterias.class);
    }

    @Bean
    Dimensions initDimensions() {
        return (Dimensions)this.client.target(Dimensions.class);
    }

    @Bean
    DistributionRules initDistributionRules() {
        return (DistributionRules)this.client.target(DistributionRules.class);
    }

    @Bean
    DNFCodeSetup initDNFCodeSetup() {
        return (DNFCodeSetup)this.client.target(DNFCodeSetup.class);
    }

    @Bean
    DownPayments initDownPayments() {
        return (DownPayments)this.client.target(DownPayments.class);
    }

    @Bean
    Drafts initDrafts() {
        return (Drafts)this.client.target(Drafts.class);
    }

    @Bean
    DunningLetters initDunningLetters() {
        return (DunningLetters)this.client.target(DunningLetters.class);
    }

    @Bean
    DunningTerms initDunningTerms() {
        return (DunningTerms)this.client.target(DunningTerms.class);
    }

    @Bean
    DynamicSystemStrings initDynamicSystemStrings() {
        return (DynamicSystemStrings)this.client.target(DynamicSystemStrings.class);
    }

    @Bean
    ElectronicFileFormats initElectronicFileFormats() {
        return (ElectronicFileFormats)this.client.target(ElectronicFileFormats.class);
    }

    @Bean
    EmailGroups initEmailGroups() {
        return (EmailGroups)this.client.target(EmailGroups.class);
    }

    @Bean
    EmployeeIDType initEmployeeIDType() {
        return (EmployeeIDType)this.client.target(EmployeeIDType.class);
    }

    @Bean
    EmployeePosition initEmployeePosition() {
        return (EmployeePosition)this.client.target(EmployeePosition.class);
    }

    @Bean
    EmployeeRolesSetup initEmployeeRolesSetup() {
        return (EmployeeRolesSetup)this.client.target(EmployeeRolesSetup.class);
    }

    @Bean
    EmployeesInfo initEmployeesInfo() {
        return (EmployeesInfo)this.client.target(EmployeesInfo.class);
    }

    @Bean
    EmployeeStatus initEmployeeStatus() {
        return (EmployeeStatus)this.client.target(EmployeeStatus.class);
    }

    @Bean
    EmployeeTransfers initEmployeeTransfers() {
        return (EmployeeTransfers)this.client.target(EmployeeTransfers.class);
    }

    @Bean
    EnhancedDiscountGroups initEnhancedDiscountGroups() {
        return (EnhancedDiscountGroups)this.client.target(EnhancedDiscountGroups.class);
    }

    @Bean
    ExpenseTypes initExpenseTypes() {
        return (ExpenseTypes)this.client.target(ExpenseTypes.class);
    }

    @Bean
    ExtendedTranslations initExtendedTranslations() {
        return (ExtendedTranslations)this.client.target(ExtendedTranslations.class);
    }

    @Bean
    FAAccountDeterminations initFAAccountDeterminations() {
        return (FAAccountDeterminations)this.client.target(FAAccountDeterminations.class);
    }

    @Bean
    FactoringIndicators initFactoringIndicators() {
        return (FactoringIndicators)this.client.target(FactoringIndicators.class);
    }

    @Bean
    FinancialYears initFinancialYears() {
        return (FinancialYears)this.client.target(FinancialYears.class);
    }

    @Bean
    FiscalPrinter initFiscalPrinter() {
        return (FiscalPrinter)this.client.target(FiscalPrinter.class);
    }

    @Bean
    FormattedSearches initFormattedSearches() {
        return (FormattedSearches)this.client.target(FormattedSearches.class);
    }

    @Bean
    FormPreferences initFormPreferences() {
        return (FormPreferences)this.client.target(FormPreferences.class);
    }

    @Bean
    Forms1099 initForms1099() {
        return (Forms1099)this.client.target(Forms1099.class);
    }

    @Bean
    GLAccountAdvancedRules initGLAccountAdvancedRules() {
        return (GLAccountAdvancedRules)this.client.target(GLAccountAdvancedRules.class);
    }

    @Bean
    GoodsReturnRequest initGoodsReturnRequest() {
        return (GoodsReturnRequest)this.client.target(GoodsReturnRequest.class);
    }

    @Bean
    GovPayCodes initGovPayCodes() {
        return (GovPayCodes)this.client.target(GovPayCodes.class);
    }

    @Bean
    HouseBankAccounts initHouseBankAccounts() {
        return (HouseBankAccounts)this.client.target(HouseBankAccounts.class);
    }

    @Bean
    IncomingPayments initIncomingPayments() {
        return (IncomingPayments)this.client.target(IncomingPayments.class);
    }

    @Bean
    IndiaSacCode initIndiaSacCode() {
        return (IndiaSacCode)this.client.target(IndiaSacCode.class);
    }

    @Bean
    Industries initIndustries() {
        return (Industries)this.client.target(Industries.class);
    }

    @Bean
    IntegrationPackagesConfigure initIntegrationPackagesConfigure() {
        return (IntegrationPackagesConfigure)this.client.target(IntegrationPackagesConfigure.class);
    }

    @Bean
    InternalReconciliations initInternalReconciliations() {
        return (InternalReconciliations)this.client.target(InternalReconciliations.class);
    }

    @Bean
    IntrastatConfiguration initIntrastatConfiguration() {
        return (IntrastatConfiguration)this.client.target(IntrastatConfiguration.class);
    }

    @Bean
    InventoryCountings initInventoryCountings() {
        return (InventoryCountings)this.client.target(InventoryCountings.class);
    }

    @Bean
    InventoryCycles initInventoryCycles() {
        return (InventoryCycles)this.client.target(InventoryCycles.class);
    }

    @Bean
    InventoryGenEntries initInventoryGenEntries() {
        return (InventoryGenEntries)this.client.target(InventoryGenEntries.class);
    }

    @Bean
    InventoryGenExits initInventoryGenExits() {
        return (InventoryGenExits)this.client.target(InventoryGenExits.class);
    }

    @Bean
    InventoryOpeningBalances initInventoryOpeningBalances() {
        return (InventoryOpeningBalances)this.client.target(InventoryOpeningBalances.class);
    }

    @Bean
    InventoryPostings initInventoryPostings() {
        return (InventoryPostings)this.client.target(InventoryPostings.class);
    }

    @Bean
    InventoryTransferRequests initInventoryTransferRequests() {
        return (InventoryTransferRequests)this.client.target(InventoryTransferRequests.class);
    }

    @Bean
    Invoices initInvoices() {
        return (Invoices)this.client.target(Invoices.class);
    }

    @Bean
    ItemGroups initItemGroups() {
        return (ItemGroups)this.client.target(ItemGroups.class);
    }

    @Bean
    ItemImages initItemImages() {
        return (ItemImages)this.client.target(ItemImages.class);
    }

    @Bean
    ItemProperties initItemProperties() {
        return (ItemProperties)this.client.target(ItemProperties.class);
    }

    @Bean
    Items initItems() {
        return (Items)this.client.target(Items.class);
    }

    @Bean
    JournalEntries initJournalEntries() {
        return (JournalEntries)this.client.target(JournalEntries.class);
    }

    @Bean
    JournalEntryDocumentTypes initJournalEntryDocumentTypes() {
        return (JournalEntryDocumentTypes)this.client.target(JournalEntryDocumentTypes.class);
    }

    @Bean
    KnowledgeBaseSolutions initKnowledgeBaseSolutions() {
        return (KnowledgeBaseSolutions)this.client.target(KnowledgeBaseSolutions.class);
    }

    @Bean
    KPIs initKPIs() {
        return (KPIs)this.client.target(KPIs.class);
    }

    @Bean
    LandedCosts initLandedCosts() {
        return (LandedCosts)this.client.target(LandedCosts.class);
    }

    @Bean
    LandedCostsCodes initLandedCostsCodes() {
        return (LandedCostsCodes)this.client.target(LandedCostsCodes.class);
    }

    @Bean
    LegalData initLegalData() {
        return (LegalData)this.client.target(LegalData.class);
    }

    @Bean
    LengthMeasures initLengthMeasures() {
        return (LengthMeasures)this.client.target(LengthMeasures.class);
    }

    @Bean
    LocalEra initLocalEra() {
        return (LocalEra)this.client.target(LocalEra.class);
    }

    @Bean
    Manufacturers initManufacturers() {
        return (Manufacturers)this.client.target(Manufacturers.class);
    }

    @Bean
    MaterialGroups initMaterialGroups() {
        return (MaterialGroups)this.client.target(MaterialGroups.class);
    }

    @Bean
    MaterialRevaluation initMaterialRevaluation() {
        return (MaterialRevaluation)this.client.target(MaterialRevaluation.class);
    }

    @Bean
    Messages initMessages() {
        return (Messages)this.client.target(Messages.class);
    }

    @Bean
    MobileAddOnSetting initMobileAddOnSetting() {
        return (MobileAddOnSetting)this.client.target(MobileAddOnSetting.class);
    }

    @Bean
    MultiLanguageTranslations initMultiLanguageTranslations() {
        return (MultiLanguageTranslations)this.client.target(MultiLanguageTranslations.class);
    }

    @Bean
    NatureOfAssessees initNatureOfAssessees() {
        return (NatureOfAssessees)this.client.target(NatureOfAssessees.class);
    }

    @Bean
    NCMCodesSetup initNCMCodesSetup() {
        return (NCMCodesSetup)this.client.target(NCMCodesSetup.class);
    }

    @Bean
    NFModels initNFModels() {
        return (NFModels)this.client.target(NFModels.class);
    }

    @Bean
    NFTaxCategories initNFTaxCategories() {
        return (NFTaxCategories)this.client.target(NFTaxCategories.class);
    }

    @Bean
    OccurrenceCodes initOccurrenceCodes() {
        return (OccurrenceCodes)this.client.target(OccurrenceCodes.class);
    }

    @Bean
    Orders initOrders() {
        return (Orders)this.client.target(Orders.class);
    }

    @Bean
    PackagesTypes initPackagesTypes() {
        return (PackagesTypes)this.client.target(PackagesTypes.class);
    }

    @Bean
    PartnersSetups initPartnersSetups() {
        return (PartnersSetups)this.client.target(PartnersSetups.class);
    }

    @Bean
    PaymentBlocks initPaymentBlocks() {
        return (PaymentBlocks)this.client.target(PaymentBlocks.class);
    }

    @Bean
    PaymentDrafts initPaymentDrafts() {
        return (PaymentDrafts)this.client.target(PaymentDrafts.class);
    }

    @Bean
    PaymentRunExport initPaymentRunExport() {
        return (PaymentRunExport)this.client.target(PaymentRunExport.class);
    }

    @Bean
    PaymentTermsTypes initPaymentTermsTypes() {
        return (PaymentTermsTypes)this.client.target(PaymentTermsTypes.class);
    }

    @Bean
    PickLists initPickLists() {
        return (PickLists)this.client.target(PickLists.class);
    }

    @Bean
    POSDailySummary initPOSDailySummary() {
        return (POSDailySummary)this.client.target(POSDailySummary.class);
    }

    @Bean
    PredefinedTexts initPredefinedTexts() {
        return (PredefinedTexts)this.client.target(PredefinedTexts.class);
    }

    @Bean
    PriceLists initPriceLists() {
        return (PriceLists)this.client.target(PriceLists.class);
    }

    @Bean
    ProductionOrders initProductionOrders() {
        return (ProductionOrders)this.client.target(ProductionOrders.class);
    }

    @Bean
    ProductTrees initProductTrees() {
        return (ProductTrees)this.client.target(ProductTrees.class);
    }

    @Bean
    ProfitCenters initProfitCenters() {
        return (ProfitCenters)this.client.target(ProfitCenters.class);
    }

    @Bean
    ProjectManagements initProjectManagements() {
        return (ProjectManagements)this.client.target(ProjectManagements.class);
    }

    @Bean
    ProjectManagementTimeSheet initProjectManagementTimeSheet() {
        return (ProjectManagementTimeSheet)this.client.target(ProjectManagementTimeSheet.class);
    }

    @Bean
    Projects initProjects() {
        return (Projects)this.client.target(Projects.class);
    }

    @Bean
    PurchaseCreditNotes initPurchaseCreditNotes() {
        return (PurchaseCreditNotes)this.client.target(PurchaseCreditNotes.class);
    }

    @Bean
    PurchaseDeliveryNotes initPurchaseDeliveryNotes() {
        return (PurchaseDeliveryNotes)this.client.target(PurchaseDeliveryNotes.class);
    }

    @Bean
    PurchaseDownPayments initPurchaseDownPayments() {
        return (PurchaseDownPayments)this.client.target(PurchaseDownPayments.class);
    }

    @Bean
    PurchaseInvoices initPurchaseInvoices() {
        return (PurchaseInvoices)this.client.target(PurchaseInvoices.class);
    }

    @Bean
    PurchaseOrders initPurchaseOrders() {
        return (PurchaseOrders)this.client.target(PurchaseOrders.class);
    }

    @Bean
    PurchaseQuotations initPurchaseQuotations() {
        return (PurchaseQuotations)this.client.target(PurchaseQuotations.class);
    }

    @Bean
    PurchaseRequests initPurchaseRequests() {
        return (PurchaseRequests)this.client.target(PurchaseRequests.class);
    }

    @Bean
    PurchaseReturns initPurchaseReturns() {
        return (PurchaseReturns)this.client.target(PurchaseReturns.class);
    }

    @Bean
    PurchaseTaxInvoices initPurchaseTaxInvoices() {
        return (PurchaseTaxInvoices)this.client.target(PurchaseTaxInvoices.class);
    }

    @Bean
    QueryAuthGroups initQueryAuthGroups() {
        return (QueryAuthGroups)this.client.target(QueryAuthGroups.class);
    }

    @Bean
    QueryCategories initQueryCategories() {
        return (QueryCategories)this.client.target(QueryCategories.class);
    }

    @Bean
    Queue initQueue() {
        return (Queue)this.client.target(Queue.class);
    }

    @Bean
    Quotations initQuotations() {
        return (Quotations)this.client.target(Quotations.class);
    }

    @Bean
    Relationships initRelationships() {
        return (Relationships)this.client.target(Relationships.class);
    }

    @Bean
    ReportFilter initReportFilter() {
        return (ReportFilter)this.client.target(ReportFilter.class);
    }

    @Bean
    ReportTypes initReportTypes() {
        return (ReportTypes)this.client.target(ReportTypes.class);
    }

    @Bean
    ResourceCapacities initResourceCapacities() {
        return (ResourceCapacities)this.client.target(ResourceCapacities.class);
    }

    @Bean
    ResourceGroups initResourceGroups() {
        return (ResourceGroups)this.client.target(ResourceGroups.class);
    }

    @Bean
    ResourceProperties initResourceProperties() {
        return (ResourceProperties)this.client.target(ResourceProperties.class);
    }

    @Bean
    Resources initResources() {
        return (Resources)this.client.target(Resources.class);
    }

    @Bean
    RetornoCodes initRetornoCodes() {
        return (RetornoCodes)this.client.target(RetornoCodes.class);
    }

    @Bean
    ReturnRequest initReturnRequest() {
        return (ReturnRequest)this.client.target(ReturnRequest.class);
    }

    @Bean
    Returns initReturns() {
        return (Returns)this.client.target(Returns.class);
    }

    @Bean
    RouteStages initRouteStages() {
        return (RouteStages)this.client.target(RouteStages.class);
    }

    @Bean
    SalesForecast initSalesForecast() {
        return (SalesForecast)this.client.target(SalesForecast.class);
    }

    @Bean
    SalesOpportunities initSalesOpportunities() {
        return (SalesOpportunities)this.client.target(SalesOpportunities.class);
    }

    @Bean
    SalesOpportunityCompetitorsSetup initSalesOpportunityCompetitorsSetup() {
        return (SalesOpportunityCompetitorsSetup)this.client.target(SalesOpportunityCompetitorsSetup.class);
    }

    @Bean
    SalesOpportunityInterestsSetup initSalesOpportunityInterestsSetup() {
        return (SalesOpportunityInterestsSetup)this.client.target(SalesOpportunityInterestsSetup.class);
    }

    @Bean
    SalesOpportunityReasonsSetup initSalesOpportunityReasonsSetup() {
        return (SalesOpportunityReasonsSetup)this.client.target(SalesOpportunityReasonsSetup.class);
    }

    @Bean
    SalesOpportunitySourcesSetup initSalesOpportunitySourcesSetup() {
        return (SalesOpportunitySourcesSetup)this.client.target(SalesOpportunitySourcesSetup.class);
    }

    @Bean
    SalesPersons initSalesPersons() {
        return (SalesPersons)this.client.target(SalesPersons.class);
    }

    @Bean
    SalesStages initSalesStages() {
        return (SalesStages)this.client.target(SalesStages.class);
    }

    @Bean
    SalesTaxAuthorities initSalesTaxAuthorities() {
        return (SalesTaxAuthorities)this.client.target(SalesTaxAuthorities.class);
    }

    @Bean
    SalesTaxAuthoritiesTypes initSalesTaxAuthoritiesTypes() {
        return (SalesTaxAuthoritiesTypes)this.client.target(SalesTaxAuthoritiesTypes.class);
    }

    @Bean
    SalesTaxCodes initSalesTaxCodes() {
        return (SalesTaxCodes)this.client.target(SalesTaxCodes.class);
    }

    @Bean
    SalesTaxInvoices initSalesTaxInvoices() {
        return (SalesTaxInvoices)this.client.target(SalesTaxInvoices.class);
    }

    @Bean
    Sections initSections() {
        return (Sections)this.client.target(Sections.class);
    }

    @Bean
    SerialNumberDetails initSerialNumberDetails() {
        return (SerialNumberDetails)this.client.target(SerialNumberDetails.class);
    }

    @Bean
    ServiceCallOrigins initServiceCallOrigins() {
        return (ServiceCallOrigins)this.client.target(ServiceCallOrigins.class);
    }

    @Bean
    ServiceCallProblemSubTypes initServiceCallProblemSubTypes() {
        return (ServiceCallProblemSubTypes)this.client.target(ServiceCallProblemSubTypes.class);
    }

    @Bean
    ServiceCallProblemTypes initServiceCallProblemTypes() {
        return (ServiceCallProblemTypes)this.client.target(ServiceCallProblemTypes.class);
    }

    @Bean
    ServiceCalls initServiceCalls() {
        return (ServiceCalls)this.client.target(ServiceCalls.class);
    }

    @Bean
    ServiceCallSolutionStatus initServiceCallSolutionStatus() {
        return (ServiceCallSolutionStatus)this.client.target(ServiceCallSolutionStatus.class);
    }

    @Bean
    ServiceCallStatus initServiceCallStatus() {
        return (ServiceCallStatus)this.client.target(ServiceCallStatus.class);
    }

    @Bean
    ServiceCallTypes initServiceCallTypes() {
        return (ServiceCallTypes)this.client.target(ServiceCallTypes.class);
    }

    @Bean
    ServiceContracts initServiceContracts() {
        return (ServiceContracts)this.client.target(ServiceContracts.class);
    }

    @Bean
    ServiceGroups initServiceGroups() {
        return (ServiceGroups)this.client.target(ServiceGroups.class);
    }

    @Bean
    ShippingTypes initShippingTypes() {
        return (ShippingTypes)this.client.target(ShippingTypes.class);
    }

    @Bean
    SpecialPrices initSpecialPrices() {
        return (SpecialPrices)this.client.target(SpecialPrices.class);
    }

    @Bean
    States initStates() {
        return (States)this.client.target(States.class);
    }

    @Bean
    StockTakings initStockTakings() {
        return (StockTakings)this.client.target(StockTakings.class);
    }

    @Bean
    StockTransferDrafts initStockTransferDrafts() {
        return (StockTransferDrafts)this.client.target(StockTransferDrafts.class);
    }

    @Bean
    StockTransfers initStockTransfers() {
        return (StockTransfers)this.client.target(StockTransfers.class);
    }

    @Bean
    TargetGroups initTargetGroups() {
        return (TargetGroups)this.client.target(TargetGroups.class);
    }

    @Bean
    TaxCodeDeterminations initTaxCodeDeterminations() {
        return (TaxCodeDeterminations)this.client.target(TaxCodeDeterminations.class);
    }

    @Bean
    TaxCodeDeterminationsTCD initTaxCodeDeterminationsTCD() {
        return (TaxCodeDeterminationsTCD)this.client.target(TaxCodeDeterminationsTCD.class);
    }

    @Bean
    TaxInvoiceReport initTaxInvoiceReport() {
        return (TaxInvoiceReport)this.client.target(TaxInvoiceReport.class);
    }

    @Bean
    TaxWebSites initTaxWebSites() {
        return (TaxWebSites)this.client.target(TaxWebSites.class);
    }

    @Bean
    Teams initTeams() {
        return (Teams)this.client.target(Teams.class);
    }

    @Bean
    TerminationReason initTerminationReason() {
        return (TerminationReason)this.client.target(TerminationReason.class);
    }

    @Bean
    Territories initTerritories() {
        return (Territories)this.client.target(Territories.class);
    }

    @Bean
    TrackingNotes initTrackingNotes() {
        return (TrackingNotes)this.client.target(TrackingNotes.class);
    }

    @Bean
    TransactionCodes initTransactionCodes() {
        return (TransactionCodes)this.client.target(TransactionCodes.class);
    }

    @Bean
    TransportationDocument initTransportationDocument() {
        return (TransportationDocument)this.client.target(TransportationDocument.class);
    }

    @Bean
    UnitOfMeasurementGroups initUnitOfMeasurementGroups() {
        return (UnitOfMeasurementGroups)this.client.target(UnitOfMeasurementGroups.class);
    }

    @Bean
    UnitOfMeasurements initUnitOfMeasurements() {
        return (UnitOfMeasurements)this.client.target(UnitOfMeasurements.class);
    }

    @Bean
    UserDefaultGroups initUserDefaultGroups() {
        return (UserDefaultGroups)this.client.target(UserDefaultGroups.class);
    }

    @Bean
    UserFieldsMD initUserFieldsMD() {
        return (UserFieldsMD)this.client.target(UserFieldsMD.class);
    }

    @Bean
    UserGroups initUserGroups() {
        return (UserGroups)this.client.target(UserGroups.class);
    }

    @Bean
    UserKeysMD initUserKeysMD() {
        return (UserKeysMD)this.client.target(UserKeysMD.class);
    }

    @Bean
    UserLanguages initUserLanguages() {
        return (UserLanguages)this.client.target(UserLanguages.class);
    }

    @Bean
    UserObjectsMD initUserObjectsMD() {
        return (UserObjectsMD)this.client.target(UserObjectsMD.class);
    }

    @Bean
    UserPermissionTree initUserPermissionTree() {
        return (UserPermissionTree)this.client.target(UserPermissionTree.class);
    }

    @Bean
    UserQueries initUserQueries() {
        return (UserQueries)this.client.target(UserQueries.class);
    }

    @Bean
    Users initUsers() {
        return (Users)this.client.target(Users.class);
    }

    @Bean
    UserTablesMD initUserTablesMD() {
        return (UserTablesMD)this.client.target(UserTablesMD.class);
    }

    @Bean
    ValueMapping initValueMapping() {
        return (ValueMapping)this.client.target(ValueMapping.class);
    }

    @Bean
    ValueMappingCommunication initValueMappingCommunication() {
        return (ValueMappingCommunication)this.client.target(ValueMappingCommunication.class);
    }

    @Bean
    VatGroups initVatGroups() {
        return (VatGroups)this.client.target(VatGroups.class);
    }

    @Bean
    VendorPayments initVendorPayments() {
        return (VendorPayments)this.client.target(VendorPayments.class);
    }

    @Bean
    WarehouseLocations initWarehouseLocations() {
        return (WarehouseLocations)this.client.target(WarehouseLocations.class);
    }

    @Bean
    Warehouses initWarehouses() {
        return (Warehouses)this.client.target(Warehouses.class);
    }

    @Bean
    WarehouseSublevelCodes initWarehouseSublevelCodes() {
        return (WarehouseSublevelCodes)this.client.target(WarehouseSublevelCodes.class);
    }

    @Bean
    WebClientBookmarkTiles initWebClientBookmarkTiles() {
        return (WebClientBookmarkTiles)this.client.target(WebClientBookmarkTiles.class);
    }

    @Bean
    WebClientDashboards initWebClientDashboards() {
        return (WebClientDashboards)this.client.target(WebClientDashboards.class);
    }

    @Bean
    WebClientFormSettings initWebClientFormSettings() {
        return (WebClientFormSettings)this.client.target(WebClientFormSettings.class);
    }

    @Bean
    WebClientLaunchpads initWebClientLaunchpads() {
        return (WebClientLaunchpads)this.client.target(WebClientLaunchpads.class);
    }

    @Bean
    WebClientListviewFilters initWebClientListviewFilters() {
        return (WebClientListviewFilters)this.client.target(WebClientListviewFilters.class);
    }

    @Bean
    WebClientNotifications initWebClientNotifications() {
        return (WebClientNotifications)this.client.target(WebClientNotifications.class);
    }

    @Bean
    WebClientPreferences initWebClientPreferences() {
        return (WebClientPreferences)this.client.target(WebClientPreferences.class);
    }

    @Bean
    WebClientRecentActivities initWebClientRecentActivities() {
        return (WebClientRecentActivities)this.client.target(WebClientRecentActivities.class);
    }

    @Bean
    WebClientVariantGroups initWebClientVariantGroups() {
        return (WebClientVariantGroups)this.client.target(WebClientVariantGroups.class);
    }

    @Bean
    WebClientVariants initWebClientVariants() {
        return (WebClientVariants)this.client.target(WebClientVariants.class);
    }

    @Bean
    WeightMeasures initWeightMeasures() {
        return (WeightMeasures)this.client.target(WeightMeasures.class);
    }

    @Bean
    WithholdingTaxCodes initWithholdingTaxCodes() {
        return (WithholdingTaxCodes)this.client.target(WithholdingTaxCodes.class);
    }

    @Bean
    WitholdingTaxDefinition initWitholdingTaxDefinition() {
        return (WitholdingTaxDefinition)this.client.target(WitholdingTaxDefinition.class);
    }

    @Bean
    WizardPaymentMethods initWizardPaymentMethods() {
        return (WizardPaymentMethods)this.client.target(WizardPaymentMethods.class);
    }

    @Bean
    AccountCategoryService initAccountCategoryService() {
        return (AccountCategoryService)this.client.target(AccountCategoryService.class);
    }

    @Bean
    AccountsService initAccountsService() {
        return (AccountsService)this.client.target(AccountsService.class);
    }

    @Bean
    AccrualTypesService initAccrualTypesService() {
        return (AccrualTypesService)this.client.target(AccrualTypesService.class);
    }

    @Bean
    ActivitiesService initActivitiesService() {
        return (ActivitiesService)this.client.target(ActivitiesService.class);
    }

    @Bean
    ActivityRecipientListsService initActivityRecipientListsService() {
        return (ActivityRecipientListsService)this.client.target(ActivityRecipientListsService.class);
    }

    @Bean
    AlternativeItemsService initAlternativeItemsService() {
        return (AlternativeItemsService)this.client.target(AlternativeItemsService.class);
    }

    @Bean
    ApprovalRequestsService initApprovalRequestsService() {
        return (ApprovalRequestsService)this.client.target(ApprovalRequestsService.class);
    }

    @Bean
    ApprovalStagesService initApprovalStagesService() {
        return (ApprovalStagesService)this.client.target(ApprovalStagesService.class);
    }

    @Bean
    ApprovalTemplatesService initApprovalTemplatesService() {
        return (ApprovalTemplatesService)this.client.target(ApprovalTemplatesService.class);
    }

    @Bean
    AssetCapitalizationCreditMemoService initAssetCapitalizationCreditMemoService() {
        return (AssetCapitalizationCreditMemoService)this.client.target(AssetCapitalizationCreditMemoService.class);
    }

    @Bean
    AssetCapitalizationService initAssetCapitalizationService() {
        return (AssetCapitalizationService)this.client.target(AssetCapitalizationService.class);
    }

    @Bean
    AssetClassesService initAssetClassesService() {
        return (AssetClassesService)this.client.target(AssetClassesService.class);
    }

    @Bean
    AssetDepreciationGroupsService initAssetDepreciationGroupsService() {
        return (AssetDepreciationGroupsService)this.client.target(AssetDepreciationGroupsService.class);
    }

    @Bean
    AssetGroupsService initAssetGroupsService() {
        return (AssetGroupsService)this.client.target(AssetGroupsService.class);
    }

    @Bean
    AssetManualDepreciationService initAssetManualDepreciationService() {
        return (AssetManualDepreciationService)this.client.target(AssetManualDepreciationService.class);
    }

    @Bean
    AssetRetirementService initAssetRetirementService() {
        return (AssetRetirementService)this.client.target(AssetRetirementService.class);
    }

    @Bean
    AssetTransferService initAssetTransferService() {
        return (AssetTransferService)this.client.target(AssetTransferService.class);
    }

    @Bean
    AttributeGroupsService initAttributeGroupsService() {
        return (AttributeGroupsService)this.client.target(AttributeGroupsService.class);
    }

    @Bean
    BankChargesAllocationCodesService initBankChargesAllocationCodesService() {
        return (BankChargesAllocationCodesService)this.client.target(BankChargesAllocationCodesService.class);
    }

    @Bean
    BankStatementsService initBankStatementsService() {
        return (BankStatementsService)this.client.target(BankStatementsService.class);
    }

    @Bean
    BarCodesService initBarCodesService() {
        return (BarCodesService)this.client.target(BarCodesService.class);
    }

    @Bean
    BinLocationAttributesService initBinLocationAttributesService() {
        return (BinLocationAttributesService)this.client.target(BinLocationAttributesService.class);
    }

    @Bean
    BinLocationFieldsService initBinLocationFieldsService() {
        return (BinLocationFieldsService)this.client.target(BinLocationFieldsService.class);
    }

    @Bean
    BinLocationsService initBinLocationsService() {
        return (BinLocationsService)this.client.target(BinLocationsService.class);
    }

    @Bean
    BlanketAgreementsService initBlanketAgreementsService() {
        return (BlanketAgreementsService)this.client.target(BlanketAgreementsService.class);
    }

    @Bean
    BOEDocumentTypesService initBOEDocumentTypesService() {
        return (BOEDocumentTypesService)this.client.target(BOEDocumentTypesService.class);
    }

    @Bean
    BOEInstructionsService initBOEInstructionsService() {
        return (BOEInstructionsService)this.client.target(BOEInstructionsService.class);
    }

    @Bean
    BOELinesService initBOELinesService() {
        return (BOELinesService)this.client.target(BOELinesService.class);
    }

    @Bean
    BOEPortfoliosService initBOEPortfoliosService() {
        return (BOEPortfoliosService)this.client.target(BOEPortfoliosService.class);
    }

    @Bean
    BPOpeningBalanceService initBPOpeningBalanceService() {
        return (BPOpeningBalanceService)this.client.target(BPOpeningBalanceService.class);
    }

    @Bean
    BranchesService initBranchesService() {
        return (BranchesService)this.client.target(BranchesService.class);
    }

    @Bean
    BrazilBeverageIndexersService initBrazilBeverageIndexersService() {
        return (BrazilBeverageIndexersService)this.client.target(BrazilBeverageIndexersService.class);
    }

    @Bean
    BrazilFuelIndexersService initBrazilFuelIndexersService() {
        return (BrazilFuelIndexersService)this.client.target(BrazilFuelIndexersService.class);
    }

    @Bean
    BusinessPartnerPropertiesService initBusinessPartnerPropertiesService() {
        return (BusinessPartnerPropertiesService)this.client.target(BusinessPartnerPropertiesService.class);
    }

    @Bean
    BusinessPartnersService initBusinessPartnersService() {
        return (BusinessPartnersService)this.client.target(BusinessPartnersService.class);
    }

    @Bean
    CampaignResponseTypeService initCampaignResponseTypeService() {
        return (CampaignResponseTypeService)this.client.target(CampaignResponseTypeService.class);
    }

    @Bean
    CampaignsService initCampaignsService() {
        return (CampaignsService)this.client.target(CampaignsService.class);
    }

    @Bean
    CashDiscountsService initCashDiscountsService() {
        return (CashDiscountsService)this.client.target(CashDiscountsService.class);
    }

    @Bean
    CashFlowLineItemsService initCashFlowLineItemsService() {
        return (CashFlowLineItemsService)this.client.target(CashFlowLineItemsService.class);
    }

    @Bean
    CertificateSeriesService initCertificateSeriesService() {
        return (CertificateSeriesService)this.client.target(CertificateSeriesService.class);
    }

    @Bean
    ChangeLogsService initChangeLogsService() {
        return (ChangeLogsService)this.client.target(ChangeLogsService.class);
    }

    @Bean
    CheckLinesService initCheckLinesService() {
        return (CheckLinesService)this.client.target(CheckLinesService.class);
    }

    @Bean
    CockpitsService initCockpitsService() {
        return (CockpitsService)this.client.target(CockpitsService.class);
    }

    @Bean
    CompanyService initCompanyService() {
        return (CompanyService)this.client.target(CompanyService.class);
    }

    @Bean
    CorrectionInvoiceReversalService initCorrectionInvoiceReversalService() {
        return (CorrectionInvoiceReversalService)this.client.target(CorrectionInvoiceReversalService.class);
    }

    @Bean
    CorrectionInvoiceService initCorrectionInvoiceService() {
        return (CorrectionInvoiceService)this.client.target(CorrectionInvoiceService.class);
    }

    @Bean
    CorrectionPurchaseInvoiceReversalService initCorrectionPurchaseInvoiceReversalService() {
        return (CorrectionPurchaseInvoiceReversalService)this.client.target(CorrectionPurchaseInvoiceReversalService.class);
    }

    @Bean
    CorrectionPurchaseInvoiceService initCorrectionPurchaseInvoiceService() {
        return (CorrectionPurchaseInvoiceService)this.client.target(CorrectionPurchaseInvoiceService.class);
    }

    @Bean
    CostCenterTypesService initCostCenterTypesService() {
        return (CostCenterTypesService)this.client.target(CostCenterTypesService.class);
    }

    @Bean
    CostElementService initCostElementService() {
        return (CostElementService)this.client.target(CostElementService.class);
    }

    @Bean
    CountriesService initCountriesService() {
        return (CountriesService)this.client.target(CountriesService.class);
    }

    @Bean
    CreditLinesService initCreditLinesService() {
        return (CreditLinesService)this.client.target(CreditLinesService.class);
    }

    @Bean
    CreditNotesService initCreditNotesService() {
        return (CreditNotesService)this.client.target(CreditNotesService.class);
    }

    @Bean
    CycleCountDeterminationsService initCycleCountDeterminationsService() {
        return (CycleCountDeterminationsService)this.client.target(CycleCountDeterminationsService.class);
    }

    @Bean
    DashboardPackagesService initDashboardPackagesService() {
        return (DashboardPackagesService)this.client.target(DashboardPackagesService.class);
    }

    @Bean
    DeductionTaxSubGroupsService initDeductionTaxSubGroupsService() {
        return (DeductionTaxSubGroupsService)this.client.target(DeductionTaxSubGroupsService.class);
    }

    @Bean
    DeliveryNotesService initDeliveryNotesService() {
        return (DeliveryNotesService)this.client.target(DeliveryNotesService.class);
    }

    @Bean
    DepartmentsService initDepartmentsService() {
        return (DepartmentsService)this.client.target(DepartmentsService.class);
    }

    @Bean
    DepositsService initDepositsService() {
        return (DepositsService)this.client.target(DepositsService.class);
    }

    @Bean
    DepreciationAreasService initDepreciationAreasService() {
        return (DepreciationAreasService)this.client.target(DepreciationAreasService.class);
    }

    @Bean
    DepreciationTypePoolsService initDepreciationTypePoolsService() {
        return (DepreciationTypePoolsService)this.client.target(DepreciationTypePoolsService.class);
    }

    @Bean
    DepreciationTypesService initDepreciationTypesService() {
        return (DepreciationTypesService)this.client.target(DepreciationTypesService.class);
    }

    @Bean
    DeterminationCriteriasService initDeterminationCriteriasService() {
        return (DeterminationCriteriasService)this.client.target(DeterminationCriteriasService.class);
    }

    @Bean
    DimensionsService initDimensionsService() {
        return (DimensionsService)this.client.target(DimensionsService.class);
    }

    @Bean
    DistributionRulesService initDistributionRulesService() {
        return (DistributionRulesService)this.client.target(DistributionRulesService.class);
    }

    @Bean
    DNFCodeSetupService initDNFCodeSetupService() {
        return (DNFCodeSetupService)this.client.target(DNFCodeSetupService.class);
    }

    @Bean
    DownPaymentsService initDownPaymentsService() {
        return (DownPaymentsService)this.client.target(DownPaymentsService.class);
    }

    @Bean
    DraftsService initDraftsService() {
        return (DraftsService)this.client.target(DraftsService.class);
    }

    @Bean
    DunningTermsService initDunningTermsService() {
        return (DunningTermsService)this.client.target(DunningTermsService.class);
    }

    @Bean
    ElectronicCommunicationActionService initElectronicCommunicationActionService() {
        return (ElectronicCommunicationActionService)this.client.target(ElectronicCommunicationActionService.class);
    }

    @Bean
    ElectronicCommunicationActionsService initElectronicCommunicationActionsService() {
        return (ElectronicCommunicationActionsService)this.client.target(ElectronicCommunicationActionsService.class);
    }

    @Bean
    ElectronicFileFormatsService initElectronicFileFormatsService() {
        return (ElectronicFileFormatsService)this.client.target(ElectronicFileFormatsService.class);
    }

    @Bean
    EmailGroupsService initEmailGroupsService() {
        return (EmailGroupsService)this.client.target(EmailGroupsService.class);
    }

    @Bean
    EmployeeIDTypeService initEmployeeIDTypeService() {
        return (EmployeeIDTypeService)this.client.target(EmployeeIDTypeService.class);
    }

    @Bean
    EmployeePositionService initEmployeePositionService() {
        return (EmployeePositionService)this.client.target(EmployeePositionService.class);
    }

    @Bean
    EmployeeRolesSetupService initEmployeeRolesSetupService() {
        return (EmployeeRolesSetupService)this.client.target(EmployeeRolesSetupService.class);
    }

    @Bean
    EmployeeStatusService initEmployeeStatusService() {
        return (EmployeeStatusService)this.client.target(EmployeeStatusService.class);
    }

    @Bean
    EmployeeTransfersService initEmployeeTransfersService() {
        return (EmployeeTransfersService)this.client.target(EmployeeTransfersService.class);
    }

    @Bean
    EnhancedDiscountGroupsService initEnhancedDiscountGroupsService() {
        return (EnhancedDiscountGroupsService)this.client.target(EnhancedDiscountGroupsService.class);
    }

    @Bean
    ExtendedTranslationsService initExtendedTranslationsService() {
        return (ExtendedTranslationsService)this.client.target(ExtendedTranslationsService.class);
    }

    @Bean
    ExternalCallsService initExternalCallsService() {
        return (ExternalCallsService)this.client.target(ExternalCallsService.class);
    }

    @Bean
    ExternalReconciliationsService initExternalReconciliationsService() {
        return (ExternalReconciliationsService)this.client.target(ExternalReconciliationsService.class);
    }

    @Bean
    FAAccountDeterminationsService initFAAccountDeterminationsService() {
        return (FAAccountDeterminationsService)this.client.target(FAAccountDeterminationsService.class);
    }

    @Bean
    FinancialYearsService initFinancialYearsService() {
        return (FinancialYearsService)this.client.target(FinancialYearsService.class);
    }

    @Bean
    FiscalPrinterService initFiscalPrinterService() {
        return (FiscalPrinterService)this.client.target(FiscalPrinterService.class);
    }

    @Bean
    FixedAssetItemsService initFixedAssetItemsService() {
        return (FixedAssetItemsService)this.client.target(FixedAssetItemsService.class);
    }

    @Bean
    GLAccountAdvancedRulesService initGLAccountAdvancedRulesService() {
        return (GLAccountAdvancedRulesService)this.client.target(GLAccountAdvancedRulesService.class);
    }

    @Bean
    GoodsReturnRequestService initGoodsReturnRequestService() {
        return (GoodsReturnRequestService)this.client.target(GoodsReturnRequestService.class);
    }

    @Bean
    GovPayCodesService initGovPayCodesService() {
        return (GovPayCodesService)this.client.target(GovPayCodesService.class);
    }

    @Bean
    GTIsService initGTIsService() {
        return (GTIsService)this.client.target(GTIsService.class);
    }

    @Bean
    IndiaSacCodeService initIndiaSacCodeService() {
        return (IndiaSacCodeService)this.client.target(IndiaSacCodeService.class);
    }

    @Bean
    IntegrationPackagesConfigureService initIntegrationPackagesConfigureService() {
        return (IntegrationPackagesConfigureService)this.client.target(IntegrationPackagesConfigureService.class);
    }

    @Bean
    InternalReconciliationsService initInternalReconciliationsService() {
        return (InternalReconciliationsService)this.client.target(InternalReconciliationsService.class);
    }

    @Bean
    IntrastatConfigurationService initIntrastatConfigurationService() {
        return (IntrastatConfigurationService)this.client.target(IntrastatConfigurationService.class);
    }

    @Bean
    InventoryCountingsService initInventoryCountingsService() {
        return (InventoryCountingsService)this.client.target(InventoryCountingsService.class);
    }

    @Bean
    InventoryGenEntryService initInventoryGenEntryService() {
        return (InventoryGenEntryService)this.client.target(InventoryGenEntryService.class);
    }

    @Bean
    InventoryGenExitService initInventoryGenExitService() {
        return (InventoryGenExitService)this.client.target(InventoryGenExitService.class);
    }

    @Bean
    InventoryOpeningBalancesService initInventoryOpeningBalancesService() {
        return (InventoryOpeningBalancesService)this.client.target(InventoryOpeningBalancesService.class);
    }

    @Bean
    InventoryPostingsService initInventoryPostingsService() {
        return (InventoryPostingsService)this.client.target(InventoryPostingsService.class);
    }

    @Bean
    InventoryTransferRequestsService initInventoryTransferRequestsService() {
        return (InventoryTransferRequestsService)this.client.target(InventoryTransferRequestsService.class);
    }

    @Bean
    InvoicesService initInvoicesService() {
        return (InvoicesService)this.client.target(InvoicesService.class);
    }

    @Bean
    JournalEntryDocumentTypeService initJournalEntryDocumentTypeService() {
        return (JournalEntryDocumentTypeService)this.client.target(JournalEntryDocumentTypeService.class);
    }

    @Bean
    JournalVouchersService initJournalVouchersService() {
        return (JournalVouchersService)this.client.target(JournalVouchersService.class);
    }

    @Bean
    KPIsService initKPIsService() {
        return (KPIsService)this.client.target(KPIsService.class);
    }

    @Bean
    LandedCostsService initLandedCostsService() {
        return (LandedCostsService)this.client.target(LandedCostsService.class);
    }

    @Bean
    LicenseService initLicenseService() {
        return (LicenseService)this.client.target(LicenseService.class);
    }

    @Bean
    Login initLogin() {
        return (Login)this.client.target(Login.class);
    }

    @Bean
    Logout initLogout() {
        return (Logout)this.client.target(Logout.class);
    }

    @Bean
    MaterialGroupsService initMaterialGroupsService() {
        return (MaterialGroupsService)this.client.target(MaterialGroupsService.class);
    }

    @Bean
    MaterialRevaluationFIFOService initMaterialRevaluationFIFOService() {
        return (MaterialRevaluationFIFOService)this.client.target(MaterialRevaluationFIFOService.class);
    }

    @Bean
    MaterialRevaluationSNBService initMaterialRevaluationSNBService() {
        return (MaterialRevaluationSNBService)this.client.target(MaterialRevaluationSNBService.class);
    }

    @Bean
    MessagesService initMessagesService() {
        return (MessagesService)this.client.target(MessagesService.class);
    }

    @Bean
    MobileAddOnSettingService initMobileAddOnSettingService() {
        return (MobileAddOnSettingService)this.client.target(MobileAddOnSettingService.class);
    }

    @Bean
    MobileAppService initMobileAppService() {
        return (MobileAppService)this.client.target(MobileAppService.class);
    }

    @Bean
    NatureOfAssesseesService initNatureOfAssesseesService() {
        return (NatureOfAssesseesService)this.client.target(NatureOfAssesseesService.class);
    }

    @Bean
    NCMCodesSetupService initNCMCodesSetupService() {
        return (NCMCodesSetupService)this.client.target(NCMCodesSetupService.class);
    }

    @Bean
    NFModelsService initNFModelsService() {
        return (NFModelsService)this.client.target(NFModelsService.class);
    }

    @Bean
    NFTaxCategoriesService initNFTaxCategoriesService() {
        return (NFTaxCategoriesService)this.client.target(NFTaxCategoriesService.class);
    }

    @Bean
    OccurrenceCodesService initOccurrenceCodesService() {
        return (OccurrenceCodesService)this.client.target(OccurrenceCodesService.class);
    }

    @Bean
    OrdersService initOrdersService() {
        return (OrdersService)this.client.target(OrdersService.class);
    }

    @Bean
    PartnersSetupsService initPartnersSetupsService() {
        return (PartnersSetupsService)this.client.target(PartnersSetupsService.class);
    }

    @Bean
    PaymentBlocksService initPaymentBlocksService() {
        return (PaymentBlocksService)this.client.target(PaymentBlocksService.class);
    }

    @Bean
    PaymentCalculationService initPaymentCalculationService() {
        return (PaymentCalculationService)this.client.target(PaymentCalculationService.class);
    }

    @Bean
    PaymentTermsTypesService initPaymentTermsTypesService() {
        return (PaymentTermsTypesService)this.client.target(PaymentTermsTypesService.class);
    }

    @Bean
    PickListsService initPickListsService() {
        return (PickListsService)this.client.target(PickListsService.class);
    }

    @Bean
    PredefinedTextsService initPredefinedTextsService() {
        return (PredefinedTextsService)this.client.target(PredefinedTextsService.class);
    }

    @Bean
    ProfitCentersService initProfitCentersService() {
        return (ProfitCentersService)this.client.target(ProfitCentersService.class);
    }

    @Bean
    ProjectManagementConfigurationService initProjectManagementConfigurationService() {
        return (ProjectManagementConfigurationService)this.client.target(ProjectManagementConfigurationService.class);
    }

    @Bean
    ProjectManagementService initProjectManagementService() {
        return (ProjectManagementService)this.client.target(ProjectManagementService.class);
    }

    @Bean
    ProjectsService initProjectsService() {
        return (ProjectsService)this.client.target(ProjectsService.class);
    }

    @Bean
    PurchaseCreditNotesService initPurchaseCreditNotesService() {
        return (PurchaseCreditNotesService)this.client.target(PurchaseCreditNotesService.class);
    }

    @Bean
    PurchaseDeliveryNotesService initPurchaseDeliveryNotesService() {
        return (PurchaseDeliveryNotesService)this.client.target(PurchaseDeliveryNotesService.class);
    }

    @Bean
    PurchaseDownPaymentsService initPurchaseDownPaymentsService() {
        return (PurchaseDownPaymentsService)this.client.target(PurchaseDownPaymentsService.class);
    }

    @Bean
    PurchaseInvoicesService initPurchaseInvoicesService() {
        return (PurchaseInvoicesService)this.client.target(PurchaseInvoicesService.class);
    }

    @Bean
    PurchaseOrdersService initPurchaseOrdersService() {
        return (PurchaseOrdersService)this.client.target(PurchaseOrdersService.class);
    }

    @Bean
    PurchaseQuotationsService initPurchaseQuotationsService() {
        return (PurchaseQuotationsService)this.client.target(PurchaseQuotationsService.class);
    }

    @Bean
    PurchaseRequestService initPurchaseRequestService() {
        return (PurchaseRequestService)this.client.target(PurchaseRequestService.class);
    }

    @Bean
    PurchaseReturnsService initPurchaseReturnsService() {
        return (PurchaseReturnsService)this.client.target(PurchaseReturnsService.class);
    }

    @Bean
    QueryAuthGroupService initQueryAuthGroupService() {
        return (QueryAuthGroupService)this.client.target(QueryAuthGroupService.class);
    }

    @Bean
    QueryService initQueryService() {
        return (QueryService)this.client.target(QueryService.class);
    }

    @Bean
    QuotationsService initQuotationsService() {
        return (QuotationsService)this.client.target(QuotationsService.class);
    }

    @Bean
    RecurringTransactionService initRecurringTransactionService() {
        return (RecurringTransactionService)this.client.target(RecurringTransactionService.class);
    }

    @Bean
    ReportFilterService initReportFilterService() {
        return (ReportFilterService)this.client.target(ReportFilterService.class);
    }

    @Bean
    ReportLayoutsService initReportLayoutsService() {
        return (ReportLayoutsService)this.client.target(ReportLayoutsService.class);
    }

    @Bean
    ReportTypesService initReportTypesService() {
        return (ReportTypesService)this.client.target(ReportTypesService.class);
    }

    @Bean
    ResourceCapacitiesService initResourceCapacitiesService() {
        return (ResourceCapacitiesService)this.client.target(ResourceCapacitiesService.class);
    }

    @Bean
    ResourceGroupsService initResourceGroupsService() {
        return (ResourceGroupsService)this.client.target(ResourceGroupsService.class);
    }

    @Bean
    ResourcePropertiesService initResourcePropertiesService() {
        return (ResourcePropertiesService)this.client.target(ResourcePropertiesService.class);
    }

    @Bean
    ResourcesService initResourcesService() {
        return (ResourcesService)this.client.target(ResourcesService.class);
    }

    @Bean
    RetornoCodesService initRetornoCodesService() {
        return (RetornoCodesService)this.client.target(RetornoCodesService.class);
    }

    @Bean
    ReturnRequestService initReturnRequestService() {
        return (ReturnRequestService)this.client.target(ReturnRequestService.class);
    }

    @Bean
    ReturnsService initReturnsService() {
        return (ReturnsService)this.client.target(ReturnsService.class);
    }

    @Bean
    RouteStagesService initRouteStagesService() {
        return (RouteStagesService)this.client.target(RouteStagesService.class);
    }

    @Bean
    RoutingDateCalculationService initRoutingDateCalculationService() {
        return (RoutingDateCalculationService)this.client.target(RoutingDateCalculationService.class);
    }

    @Bean
    SalesOpportunityCompetitorsSetupService initSalesOpportunityCompetitorsSetupService() {
        return (SalesOpportunityCompetitorsSetupService)this.client.target(SalesOpportunityCompetitorsSetupService.class);
    }

    @Bean
    SalesOpportunityInterestsSetupService initSalesOpportunityInterestsSetupService() {
        return (SalesOpportunityInterestsSetupService)this.client.target(SalesOpportunityInterestsSetupService.class);
    }

    @Bean
    SalesOpportunityReasonsSetupService initSalesOpportunityReasonsSetupService() {
        return (SalesOpportunityReasonsSetupService)this.client.target(SalesOpportunityReasonsSetupService.class);
    }

    @Bean
    SalesOpportunitySourcesSetupService initSalesOpportunitySourcesSetupService() {
        return (SalesOpportunitySourcesSetupService)this.client.target(SalesOpportunitySourcesSetupService.class);
    }

    @Bean
    SBOBobService initSBOBobService() {
        return (SBOBobService)this.client.target(SBOBobService.class);
    }

    @Bean
    SectionsService initSectionsService() {
        return (SectionsService)this.client.target(SectionsService.class);
    }

    @Bean
    SensitiveDataAccessService initSensitiveDataAccessService() {
        return (SensitiveDataAccessService)this.client.target(SensitiveDataAccessService.class);
    }

    @Bean
    SeriesService initSeriesService() {
        return (SeriesService)this.client.target(SeriesService.class);
    }

    @Bean
    ServiceCallOriginsService initServiceCallOriginsService() {
        return (ServiceCallOriginsService)this.client.target(ServiceCallOriginsService.class);
    }

    @Bean
    ServiceCallProblemSubTypesService initServiceCallProblemSubTypesService() {
        return (ServiceCallProblemSubTypesService)this.client.target(ServiceCallProblemSubTypesService.class);
    }

    @Bean
    ServiceCallProblemTypesService initServiceCallProblemTypesService() {
        return (ServiceCallProblemTypesService)this.client.target(ServiceCallProblemTypesService.class);
    }

    @Bean
    ServiceCallSolutionStatusService initServiceCallSolutionStatusService() {
        return (ServiceCallSolutionStatusService)this.client.target(ServiceCallSolutionStatusService.class);
    }

    @Bean
    ServiceCallStatusService initServiceCallStatusService() {
        return (ServiceCallStatusService)this.client.target(ServiceCallStatusService.class);
    }

    @Bean
    ServiceCallTypesService initServiceCallTypesService() {
        return (ServiceCallTypesService)this.client.target(ServiceCallTypesService.class);
    }

    @Bean
    ServiceGroupsService initServiceGroupsService() {
        return (ServiceGroupsService)this.client.target(ServiceGroupsService.class);
    }

    @Bean
    ServiceTaxPostingService initServiceTaxPostingService() {
        return (ServiceTaxPostingService)this.client.target(ServiceTaxPostingService.class);
    }

    @Bean
    StatesService initStatesService() {
        return (StatesService)this.client.target(StatesService.class);
    }

    @Bean
    StockTransferDraftService initStockTransferDraftService() {
        return (StockTransferDraftService)this.client.target(StockTransferDraftService.class);
    }

    @Bean
    StockTransferService initStockTransferService() {
        return (StockTransferService)this.client.target(StockTransferService.class);
    }

    @Bean
    TargetGroupsService initTargetGroupsService() {
        return (TargetGroupsService)this.client.target(TargetGroupsService.class);
    }

    @Bean
    TaxCodeDeterminationsService initTaxCodeDeterminationsService() {
        return (TaxCodeDeterminationsService)this.client.target(TaxCodeDeterminationsService.class);
    }

    @Bean
    TaxCodeDeterminationsTCDService initTaxCodeDeterminationsTCDService() {
        return (TaxCodeDeterminationsTCDService)this.client.target(TaxCodeDeterminationsTCDService.class);
    }

    @Bean
    TaxWebSitesService initTaxWebSitesService() {
        return (TaxWebSitesService)this.client.target(TaxWebSitesService.class);
    }

    @Bean
    TerminationReasonService initTerminationReasonService() {
        return (TerminationReasonService)this.client.target(TerminationReasonService.class);
    }

    @Bean
    TrackingNotesService initTrackingNotesService() {
        return (TrackingNotesService)this.client.target(TrackingNotesService.class);
    }

    @Bean
    TransactionCodesService initTransactionCodesService() {
        return (TransactionCodesService)this.client.target(TransactionCodesService.class);
    }

    @Bean
    UnitOfMeasurementGroupsService initUnitOfMeasurementGroupsService() {
        return (UnitOfMeasurementGroupsService)this.client.target(UnitOfMeasurementGroupsService.class);
    }

    @Bean
    UnitOfMeasurementsService initUnitOfMeasurementsService() {
        return (UnitOfMeasurementsService)this.client.target(UnitOfMeasurementsService.class);
    }

    @Bean
    UserGroupService initUserGroupService() {
        return (UserGroupService)this.client.target(UserGroupService.class);
    }

    @Bean
    UserMenuService initUserMenuService() {
        return (UserMenuService)this.client.target(UserMenuService.class);
    }

    @Bean
    ValueMappingService initValueMappingService() {
        return (ValueMappingService)this.client.target(ValueMappingService.class);
    }

    @Bean
    WarehouseSublevelCodesService initWarehouseSublevelCodesService() {
        return (WarehouseSublevelCodesService)this.client.target(WarehouseSublevelCodesService.class);
    }

    @Bean
    WebClientBookmarkTileService initWebClientBookmarkTileService() {
        return (WebClientBookmarkTileService)this.client.target(WebClientBookmarkTileService.class);
    }

    @Bean
    WebClientDashboardService initWebClientDashboardService() {
        return (WebClientDashboardService)this.client.target(WebClientDashboardService.class);
    }

    @Bean
    WebClientFormSettingService initWebClientFormSettingService() {
        return (WebClientFormSettingService)this.client.target(WebClientFormSettingService.class);
    }

    @Bean
    WebClientLaunchpadService initWebClientLaunchpadService() {
        return (WebClientLaunchpadService)this.client.target(WebClientLaunchpadService.class);
    }

    @Bean
    WebClientListviewFilterService initWebClientListviewFilterService() {
        return (WebClientListviewFilterService)this.client.target(WebClientListviewFilterService.class);
    }

    @Bean
    WebClientNotificationService initWebClientNotificationService() {
        return (WebClientNotificationService)this.client.target(WebClientNotificationService.class);
    }

    @Bean
    WebClientPreferenceService initWebClientPreferenceService() {
        return (WebClientPreferenceService)this.client.target(WebClientPreferenceService.class);
    }

    @Bean
    WebClientRecentActivityService initWebClientRecentActivityService() {
        return (WebClientRecentActivityService)this.client.target(WebClientRecentActivityService.class);
    }

    @Bean
    WebClientVariantGroupService initWebClientVariantGroupService() {
        return (WebClientVariantGroupService)this.client.target(WebClientVariantGroupService.class);
    }

    @Bean
    WebClientVariantService initWebClientVariantService() {
        return (WebClientVariantService)this.client.target(WebClientVariantService.class);
    }

    @Bean
    WorkflowTaskService initWorkflowTaskService() {
        return (WorkflowTaskService)this.client.target(WorkflowTaskService.class);
    }
}

