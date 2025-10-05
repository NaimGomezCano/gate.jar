/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.sap.sme.anw.analytics.core.base.AnalyticsCoreException
 *  com.sap.sme.anw.analytics.core.context.ContextDataHolder
 *  com.sap.sme.anw.analytics.core.context.IContextData
 *  com.sap.sme.anw.analytics.core.log.AnalyticsLog
 *  com.sap.sme.anw.analytics.core.log.AnalyticsLogLevel
 *  com.sap.sme.anw.analytics.core.model.service.IModelService
 *  com.sap.sme.anw.analytics.core.odata.ODataUtils
 *  com.sap.sme.anw.analytics.core.service.ServiceFactory
 *  com.sap.sme.anw.analytics.thin.odata.ODataServiceUtils
 *  com.sap.sme.anw.analytics.thin.odata.odata4.ODataService4EdmProvider
 *  com.sap.sme.anw.analytics.thin.odata.odata4.ODataService4EdmProvider$1
 *  com.sap.sme.anw.analytics.type.model.AnalysisObject
 *  com.sap.sme.anw.analytics.type.model.AnalysisObjectType
 *  com.sap.sme.anw.analytics.type.model.ModelMetaObject
 *  com.sap.sme.anw.analytics.type.model.ModelMetaResponse
 *  com.sap.sme.anw.analytics.type.model.ModelObject
 *  com.sap.sme.anw.analytics.type.model.ModelType
 *  com.sap.sme.anw.analytics.type.model.analysisobjectmeta.AnalysisObjectDataType
 *  com.sap.sme.anw.analytics.type.model.analysisobjectmeta.AnalysisObjectMeta
 *  org.apache.olingo.commons.api.edm.EdmPrimitiveTypeKind
 *  org.apache.olingo.commons.api.edm.FullQualifiedName
 *  org.apache.olingo.commons.api.edm.provider.CsdlAbstractEdmProvider
 *  org.apache.olingo.commons.api.edm.provider.CsdlEntityContainer
 *  org.apache.olingo.commons.api.edm.provider.CsdlEntityContainerInfo
 *  org.apache.olingo.commons.api.edm.provider.CsdlEntitySet
 *  org.apache.olingo.commons.api.edm.provider.CsdlEntityType
 *  org.apache.olingo.commons.api.edm.provider.CsdlProperty
 *  org.apache.olingo.commons.api.edm.provider.CsdlSchema
 *  org.apache.olingo.commons.api.ex.ODataException
 */
package com.sap.sme.anw.analytics.thin.odata.odata4;

import com.sap.sme.anw.analytics.core.base.AnalyticsCoreException;
import com.sap.sme.anw.analytics.core.context.ContextDataHolder;
import com.sap.sme.anw.analytics.core.context.IContextData;
import com.sap.sme.anw.analytics.core.log.AnalyticsLog;
import com.sap.sme.anw.analytics.core.log.AnalyticsLogLevel;
import com.sap.sme.anw.analytics.core.model.service.IModelService;
import com.sap.sme.anw.analytics.core.odata.ODataUtils;
import com.sap.sme.anw.analytics.core.service.ServiceFactory;
import com.sap.sme.anw.analytics.thin.odata.ODataServiceUtils;
import com.sap.sme.anw.analytics.thin.odata.odata4.ODataService4EdmProvider;
import com.sap.sme.anw.analytics.type.model.AnalysisObject;
import com.sap.sme.anw.analytics.type.model.AnalysisObjectType;
import com.sap.sme.anw.analytics.type.model.ModelMetaObject;
import com.sap.sme.anw.analytics.type.model.ModelMetaResponse;
import com.sap.sme.anw.analytics.type.model.ModelObject;
import com.sap.sme.anw.analytics.type.model.ModelType;
import com.sap.sme.anw.analytics.type.model.analysisobjectmeta.AnalysisObjectDataType;
import com.sap.sme.anw.analytics.type.model.analysisobjectmeta.AnalysisObjectMeta;
import java.util.ArrayList;
import java.util.List;
import org.apache.olingo.commons.api.edm.EdmPrimitiveTypeKind;
import org.apache.olingo.commons.api.edm.FullQualifiedName;
import org.apache.olingo.commons.api.edm.provider.CsdlAbstractEdmProvider;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityContainer;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityContainerInfo;
import org.apache.olingo.commons.api.edm.provider.CsdlEntitySet;
import org.apache.olingo.commons.api.edm.provider.CsdlEntityType;
import org.apache.olingo.commons.api.edm.provider.CsdlProperty;
import org.apache.olingo.commons.api.edm.provider.CsdlSchema;
import org.apache.olingo.commons.api.ex.ODataException;

public class ODataService4EdmProvider
extends CsdlAbstractEdmProvider {
    private static final FullQualifiedName ENTITY_CONTAINER_DATASOURCE_NAME = new FullQualifiedName("DataSource", "EntityContainerDataSource");

    public List<CsdlSchema> getSchemas() throws ODataException {
        List dataSourceModelObjects = ODataServiceUtils.getDataSourceModelObjects();
        ArrayList<CsdlEntityType> entityTypes = new ArrayList<CsdlEntityType>(3);
        for (ModelObject modelObject : dataSourceModelObjects) {
            String modelId = modelObject.getModelId();
            FullQualifiedName entityTypeFQName = new FullQualifiedName("DataSource", ODataUtils.formatId((String)modelId));
            CsdlEntityType entityType = this.getEntityType(entityTypeFQName);
            if (entityType == null) continue;
            entityTypes.add(entityType);
        }
        CsdlEntityContainer entityContainer = this.getEntityContainer();
        ArrayList<CsdlSchema> schemas = new ArrayList<CsdlSchema>();
        CsdlSchema schema = new CsdlSchema().setNamespace("DataSource").setEntityTypes(entityTypes).setEntityContainer(entityContainer);
        schemas.add(schema);
        return schemas;
    }

    public CsdlEntityType getEntityType(FullQualifiedName entityTypeName) throws ODataException {
        ModelMetaResponse modelMetaResponse;
        String name = entityTypeName.getName();
        if (name == null) {
            return null;
        }
        IContextData contextData = ContextDataHolder.getContextData();
        String modelId = ODataUtils.unFormatId((String)name);
        IModelService modelService = (IModelService)ServiceFactory.getService(IModelService.class);
        ModelMetaObject modelMetaObject = new ModelMetaObject();
        modelMetaObject.setModelId(modelId);
        modelMetaObject.setModelType(ModelType.DATASOURCE);
        try {
            modelMetaResponse = modelService.getModelMeta(contextData, modelMetaObject);
        }
        catch (AnalyticsCoreException e) {
            AnalyticsLog.log(ODataServiceUtils.class, (AnalyticsLogLevel)AnalyticsLogLevel.DEBUG, (Throwable)e);
            return null;
        }
        List analysisObjects = modelMetaResponse.getAnalysisObjects();
        ArrayList<CsdlProperty> properties = new ArrayList<CsdlProperty>();
        for (AnalysisObject analysisObject : analysisObjects) {
            AnalysisObjectDataType dataType;
            String id = analysisObject.getId();
            AnalysisObjectType type = analysisObject.getType();
            AnalysisObjectMeta meta = analysisObject.getMeta();
            EdmPrimitiveTypeKind edmType = type == AnalysisObjectType.MEASURE ? EdmPrimitiveTypeKind.Decimal : EdmPrimitiveTypeKind.String;
            if (meta != null && (dataType = meta.getDataType()) != null) {
                switch (1.$SwitchMap$com$sap$sme$anw$analytics$type$model$analysisobjectmeta$AnalysisObjectDataType[dataType.ordinal()]) {
                    case 1: 
                    case 2: {
                        edmType = EdmPrimitiveTypeKind.Int32;
                        break;
                    }
                    case 3: {
                        edmType = EdmPrimitiveTypeKind.Decimal;
                        break;
                    }
                    case 4: {
                        edmType = EdmPrimitiveTypeKind.Double;
                        break;
                    }
                    case 5: 
                    case 6: {
                        edmType = EdmPrimitiveTypeKind.Date;
                        break;
                    }
                    case 7: {
                        edmType = EdmPrimitiveTypeKind.TimeOfDay;
                        break;
                    }
                    default: {
                        edmType = EdmPrimitiveTypeKind.String;
                    }
                }
            }
            properties.add(new CsdlProperty().setName(id).setType(edmType.getFullQualifiedName()));
        }
        CsdlEntityType entityType = new CsdlEntityType().setName(name).setProperties(properties);
        return entityType;
    }

    public CsdlEntityContainer getEntityContainer() throws ODataException {
        List dataSourceModelObjects = ODataServiceUtils.getDataSourceModelObjects();
        ArrayList<CsdlEntitySet> entitySets = new ArrayList<CsdlEntitySet>();
        for (ModelObject modelObject : dataSourceModelObjects) {
            String modelId = modelObject.getModelId();
            String entitySetName = ODataUtils.formatId((String)modelId);
            CsdlEntitySet entitySet = this.getEntitySet(ENTITY_CONTAINER_DATASOURCE_NAME, entitySetName);
            entitySets.add(entitySet);
        }
        CsdlEntityContainer entityContainer = new CsdlEntityContainer().setName("EntityContainerDataSource").setEntitySets(entitySets);
        return entityContainer;
    }

    public CsdlEntityContainerInfo getEntityContainerInfo(FullQualifiedName entityContainerName) throws ODataException {
        if (entityContainerName == null || ENTITY_CONTAINER_DATASOURCE_NAME.equals((Object)entityContainerName)) {
            CsdlEntityContainerInfo entityContainerInfo = new CsdlEntityContainerInfo().setContainerName(ENTITY_CONTAINER_DATASOURCE_NAME);
            return entityContainerInfo;
        }
        return null;
    }

    public CsdlEntitySet getEntitySet(FullQualifiedName entityContainer, String entitySetName) throws ODataException {
        if (entityContainer == null || ENTITY_CONTAINER_DATASOURCE_NAME.equals((Object)entityContainer)) {
            if (entitySetName == null) {
                return null;
            }
            FullQualifiedName entityTypeFQName = new FullQualifiedName("DataSource", entitySetName);
            CsdlEntitySet entitySet = new CsdlEntitySet().setName(entitySetName).setType(entityTypeFQName);
            return entitySet;
        }
        return null;
    }
}

