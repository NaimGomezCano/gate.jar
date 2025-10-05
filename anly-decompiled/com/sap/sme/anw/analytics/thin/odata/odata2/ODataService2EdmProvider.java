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
 *  com.sap.sme.anw.analytics.thin.odata.odata2.ODataService2EdmProvider
 *  com.sap.sme.anw.analytics.thin.odata.odata2.ODataService2EdmProvider$1
 *  com.sap.sme.anw.analytics.type.model.AnalysisObject
 *  com.sap.sme.anw.analytics.type.model.AnalysisObjectType
 *  com.sap.sme.anw.analytics.type.model.ModelMetaObject
 *  com.sap.sme.anw.analytics.type.model.ModelMetaResponse
 *  com.sap.sme.anw.analytics.type.model.ModelObject
 *  com.sap.sme.anw.analytics.type.model.ModelType
 *  com.sap.sme.anw.analytics.type.model.analysisobjectmeta.AnalysisObjectDataType
 *  com.sap.sme.anw.analytics.type.model.analysisobjectmeta.AnalysisObjectMeta
 *  org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind
 *  org.apache.olingo.odata2.api.edm.FullQualifiedName
 *  org.apache.olingo.odata2.api.edm.provider.EdmProvider
 *  org.apache.olingo.odata2.api.edm.provider.EntityContainer
 *  org.apache.olingo.odata2.api.edm.provider.EntityContainerInfo
 *  org.apache.olingo.odata2.api.edm.provider.EntitySet
 *  org.apache.olingo.odata2.api.edm.provider.EntityType
 *  org.apache.olingo.odata2.api.edm.provider.Schema
 *  org.apache.olingo.odata2.api.edm.provider.SimpleProperty
 *  org.apache.olingo.odata2.api.exception.ODataException
 */
package com.sap.sme.anw.analytics.thin.odata.odata2;

import com.sap.sme.anw.analytics.core.base.AnalyticsCoreException;
import com.sap.sme.anw.analytics.core.context.ContextDataHolder;
import com.sap.sme.anw.analytics.core.context.IContextData;
import com.sap.sme.anw.analytics.core.log.AnalyticsLog;
import com.sap.sme.anw.analytics.core.log.AnalyticsLogLevel;
import com.sap.sme.anw.analytics.core.model.service.IModelService;
import com.sap.sme.anw.analytics.core.odata.ODataUtils;
import com.sap.sme.anw.analytics.core.service.ServiceFactory;
import com.sap.sme.anw.analytics.thin.odata.ODataServiceUtils;
import com.sap.sme.anw.analytics.thin.odata.odata2.ODataService2EdmProvider;
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
import org.apache.olingo.odata2.api.edm.EdmSimpleTypeKind;
import org.apache.olingo.odata2.api.edm.FullQualifiedName;
import org.apache.olingo.odata2.api.edm.provider.EdmProvider;
import org.apache.olingo.odata2.api.edm.provider.EntityContainer;
import org.apache.olingo.odata2.api.edm.provider.EntityContainerInfo;
import org.apache.olingo.odata2.api.edm.provider.EntitySet;
import org.apache.olingo.odata2.api.edm.provider.EntityType;
import org.apache.olingo.odata2.api.edm.provider.Schema;
import org.apache.olingo.odata2.api.edm.provider.SimpleProperty;
import org.apache.olingo.odata2.api.exception.ODataException;

public class ODataService2EdmProvider
extends EdmProvider {
    public List<Schema> getSchemas() throws ODataException {
        List dataSourceModelObjects = ODataServiceUtils.getDataSourceModelObjects();
        ArrayList<EntityType> entityTypes = new ArrayList<EntityType>();
        for (ModelObject modelObject : dataSourceModelObjects) {
            String modelId = modelObject.getModelId();
            FullQualifiedName entityTypeFQName = new FullQualifiedName("DataSource", ODataUtils.formatId((String)modelId));
            EntityType entityType = this.getEntityType(entityTypeFQName);
            if (entityType == null) continue;
            entityTypes.add(entityType);
        }
        ArrayList<EntityContainer> entityContainers = new ArrayList<EntityContainer>();
        EntityContainerInfo entityContainerInfo = this.getEntityContainerInfo("EntityContainerDataSource");
        entityContainers.add((EntityContainer)entityContainerInfo);
        ArrayList<Schema> schemas = new ArrayList<Schema>();
        Schema schema = new Schema().setNamespace("DataSource").setEntityTypes(entityTypes).setEntityContainers(entityContainers);
        schemas.add(schema);
        return schemas;
    }

    public EntityType getEntityType(FullQualifiedName edmFQName) throws ODataException {
        ModelMetaResponse modelMetaResponse;
        String name = edmFQName.getName();
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
        ArrayList<SimpleProperty> properties = new ArrayList<SimpleProperty>();
        for (AnalysisObject analysisObject : analysisObjects) {
            AnalysisObjectDataType dataType;
            String id = analysisObject.getId();
            AnalysisObjectType type = analysisObject.getType();
            AnalysisObjectMeta meta = analysisObject.getMeta();
            EdmSimpleTypeKind edmType = type == AnalysisObjectType.MEASURE ? EdmSimpleTypeKind.Decimal : EdmSimpleTypeKind.String;
            if (meta != null && (dataType = meta.getDataType()) != null) {
                switch (1.$SwitchMap$com$sap$sme$anw$analytics$type$model$analysisobjectmeta$AnalysisObjectDataType[dataType.ordinal()]) {
                    case 1: 
                    case 2: {
                        edmType = EdmSimpleTypeKind.Int32;
                        break;
                    }
                    case 3: {
                        edmType = EdmSimpleTypeKind.Decimal;
                        break;
                    }
                    case 4: {
                        edmType = EdmSimpleTypeKind.Double;
                        break;
                    }
                    case 5: 
                    case 6: {
                        edmType = EdmSimpleTypeKind.DateTime;
                        break;
                    }
                    case 7: {
                        edmType = EdmSimpleTypeKind.Time;
                        break;
                    }
                    default: {
                        edmType = EdmSimpleTypeKind.String;
                    }
                }
            }
            properties.add(new SimpleProperty().setName(id).setType(edmType));
        }
        EntityType entityType = new EntityType().setName(name).setProperties(properties);
        return entityType;
    }

    public EntityContainerInfo getEntityContainerInfo(String name) throws ODataException {
        if (name == null || "EntityContainerDataSource".equals(name)) {
            List dataSourceModelObjects = ODataServiceUtils.getDataSourceModelObjects();
            ArrayList<EntitySet> entitySets = new ArrayList<EntitySet>();
            for (ModelObject modelObject : dataSourceModelObjects) {
                String modelId = modelObject.getModelId();
                String entitySetName = ODataUtils.formatId((String)modelId);
                EntitySet entitySet = this.getEntitySet(name, entitySetName);
                entitySets.add(entitySet);
            }
            EntityContainer entityContainer = new EntityContainer().setName(name).setDefaultEntityContainer(true).setEntitySets(entitySets);
            return entityContainer;
        }
        return null;
    }

    public EntitySet getEntitySet(String entityContainer, String name) throws ODataException {
        if (entityContainer == null || "EntityContainerDataSource".equals(entityContainer)) {
            if (name == null) {
                return null;
            }
            FullQualifiedName entityTypeFQName = new FullQualifiedName("DataSource", name);
            EntitySet entitySet = new EntitySet().setName(name).setEntityType(entityTypeFQName);
            return entitySet;
        }
        return null;
    }
}

