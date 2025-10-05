/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.fasterxml.jackson.annotation.JsonAutoDetect$Visibility
 *  com.fasterxml.jackson.annotation.JsonInclude$Include
 *  com.fasterxml.jackson.annotation.PropertyAccessor
 *  com.fasterxml.jackson.databind.DeserializationFeature
 *  com.fasterxml.jackson.databind.MapperFeature
 *  com.fasterxml.jackson.databind.Module
 *  com.fasterxml.jackson.databind.ObjectMapper
 *  com.fasterxml.jackson.databind.SerializationFeature
 *  com.fasterxml.jackson.databind.json.JsonMapper
 *  com.fasterxml.jackson.databind.json.JsonMapper$Builder
 *  com.fasterxml.jackson.databind.module.SimpleModule
 *  com.sap.b1.svcl.client.utils.ServiceLayerErrorDecoder
 *  com.sap.b1.tcli.observer.feign.ADM1Feign
 *  com.sap.b1.tcli.observer.feign.AddressFeign
 *  com.sap.b1.tcli.observer.feign.CUDVFeign
 *  com.sap.b1.tcli.observer.feign.CUFDFeign
 *  com.sap.b1.tcli.observer.feign.CUVEFeign
 *  com.sap.b1.tcli.observer.feign.CUVFFeign
 *  com.sap.b1.tcli.observer.feign.DSC1Feign
 *  com.sap.b1.tcli.observer.feign.MessageFeign
 *  com.sap.b1.tcli.observer.feign.OAARFeign
 *  com.sap.b1.tcli.observer.feign.OACGFeign
 *  com.sap.b1.tcli.observer.feign.OACPFeign
 *  com.sap.b1.tcli.observer.feign.OACTFeign
 *  com.sap.b1.tcli.observer.feign.OADFFeign
 *  com.sap.b1.tcli.observer.feign.OADMFeign
 *  com.sap.b1.tcli.observer.feign.OADPFeign
 *  com.sap.b1.tcli.observer.feign.OALCFeign
 *  com.sap.b1.tcli.observer.feign.OALIFeign
 *  com.sap.b1.tcli.observer.feign.OARGFeign
 *  com.sap.b1.tcli.observer.feign.OASGFeign
 *  com.sap.b1.tcli.observer.feign.OATCFeign
 *  com.sap.b1.tcli.observer.feign.OBCAFeign
 *  com.sap.b1.tcli.observer.feign.OBINFeign
 *  com.sap.b1.tcli.observer.feign.OBNIFeign
 *  com.sap.b1.tcli.observer.feign.OBPLFeign
 *  com.sap.b1.tcli.observer.feign.OBPPFeign
 *  com.sap.b1.tcli.observer.feign.OBSIFeign
 *  com.sap.b1.tcli.observer.feign.OBSJFeign
 *  com.sap.b1.tcli.observer.feign.OBTDFeign
 *  com.sap.b1.tcli.observer.feign.OBTFFeign
 *  com.sap.b1.tcli.observer.feign.OCBIFeign
 *  com.sap.b1.tcli.observer.feign.OCDCFeign
 *  com.sap.b1.tcli.observer.feign.OCDTFeign
 *  com.sap.b1.tcli.observer.feign.OCIFFeign
 *  com.sap.b1.tcli.observer.feign.OCLAFeign
 *  com.sap.b1.tcli.observer.feign.OCLGFeign
 *  com.sap.b1.tcli.observer.feign.OCLOFeign
 *  com.sap.b1.tcli.observer.feign.OCLSFeign
 *  com.sap.b1.tcli.observer.feign.OCLTFeign
 *  com.sap.b1.tcli.observer.feign.OCMTFeign
 *  com.sap.b1.tcli.observer.feign.OCNAFeign
 *  com.sap.b1.tcli.observer.feign.OCOGFeign
 *  com.sap.b1.tcli.observer.feign.OCPNFeign
 *  com.sap.b1.tcli.observer.feign.OCQGFeign
 *  com.sap.b1.tcli.observer.feign.OCRCFeign
 *  com.sap.b1.tcli.observer.feign.OCRDFeign
 *  com.sap.b1.tcli.observer.feign.OCRGFeign
 *  com.sap.b1.tcli.observer.feign.OCRNFeign
 *  com.sap.b1.tcli.observer.feign.OCRPFeign
 *  com.sap.b1.tcli.observer.feign.OCRYFeign
 *  com.sap.b1.tcli.observer.feign.OCSTFeign
 *  com.sap.b1.tcli.observer.feign.OCTGFeign
 *  com.sap.b1.tcli.observer.feign.OCTRFeign
 *  com.sap.b1.tcli.observer.feign.OCTTFeign
 *  com.sap.b1.tcli.observer.feign.OCYCFeign
 *  com.sap.b1.tcli.observer.feign.ODCIFeign
 *  com.sap.b1.tcli.observer.feign.ODDGFeign
 *  com.sap.b1.tcli.observer.feign.ODGLFeign
 *  com.sap.b1.tcli.observer.feign.ODGPFeign
 *  com.sap.b1.tcli.observer.feign.ODLNFeign
 *  com.sap.b1.tcli.observer.feign.ODPIFeign
 *  com.sap.b1.tcli.observer.feign.ODPOFeign
 *  com.sap.b1.tcli.observer.feign.ODPSFeign
 *  com.sap.b1.tcli.observer.feign.ODPTFeign
 *  com.sap.b1.tcli.observer.feign.ODRFFeign
 *  com.sap.b1.tcli.observer.feign.ODSCFeign
 *  com.sap.b1.tcli.observer.feign.ODUNFeign
 *  com.sap.b1.tcli.observer.feign.OEDGFeign
 *  com.sap.b1.tcli.observer.feign.OEFCFeign
 *  com.sap.b1.tcli.observer.feign.OEGPFeign
 *  com.sap.b1.tcli.observer.feign.OEMLFeign
 *  com.sap.b1.tcli.observer.feign.OEOTFeign
 *  com.sap.b1.tcli.observer.feign.OEPEFeign
 *  com.sap.b1.tcli.observer.feign.OETCFeign
 *  com.sap.b1.tcli.observer.feign.OEXDFeign
 *  com.sap.b1.tcli.observer.feign.OFCTFeign
 *  com.sap.b1.tcli.observer.feign.OFPRFeign
 *  com.sap.b1.tcli.observer.feign.OGDRFeign
 *  com.sap.b1.tcli.observer.feign.OHEMFeign
 *  com.sap.b1.tcli.observer.feign.OICDFeign
 *  com.sap.b1.tcli.observer.feign.OIDCFeign
 *  com.sap.b1.tcli.observer.feign.OIDXFeign
 *  com.sap.b1.tcli.observer.feign.OIGEFeign
 *  com.sap.b1.tcli.observer.feign.OIGEFeign2
 *  com.sap.b1.tcli.observer.feign.OIGNFeign
 *  com.sap.b1.tcli.observer.feign.OIGNFeign2
 *  com.sap.b1.tcli.observer.feign.OINCFeign
 *  com.sap.b1.tcli.observer.feign.OINSFeign
 *  com.sap.b1.tcli.observer.feign.OINVFeign
 *  com.sap.b1.tcli.observer.feign.OIPFFeign
 *  com.sap.b1.tcli.observer.feign.OIQIFeign
 *  com.sap.b1.tcli.observer.feign.OIQRFeign
 *  com.sap.b1.tcli.observer.feign.OISWFeign
 *  com.sap.b1.tcli.observer.feign.OITBFeign
 *  com.sap.b1.tcli.observer.feign.OITGFeign
 *  com.sap.b1.tcli.observer.feign.OITMFeign
 *  com.sap.b1.tcli.observer.feign.OITTFeign
 *  com.sap.b1.tcli.observer.feign.OJDTFeign
 *  com.sap.b1.tcli.observer.feign.OLCTFeign
 *  com.sap.b1.tcli.observer.feign.OLGTFeign
 *  com.sap.b1.tcli.observer.feign.OLNGFeign
 *  com.sap.b1.tcli.observer.feign.OMLTFeign
 *  com.sap.b1.tcli.observer.feign.OMPFFeign
 *  com.sap.b1.tcli.observer.feign.OMRCFeign
 *  com.sap.b1.tcli.observer.feign.OOATFeign
 *  com.sap.b1.tcli.observer.feign.OOCRFeign
 *  com.sap.b1.tcli.observer.feign.OOFRFeign
 *  com.sap.b1.tcli.observer.feign.OOINFeign
 *  com.sap.b1.tcli.observer.feign.OOIRFeign
 *  com.sap.b1.tcli.observer.feign.OONDFeign
 *  com.sap.b1.tcli.observer.feign.OOPBFeign
 *  com.sap.b1.tcli.observer.feign.OORLFeign
 *  com.sap.b1.tcli.observer.feign.OOSRFeign
 *  com.sap.b1.tcli.observer.feign.OOSTFeign
 *  com.sap.b1.tcli.observer.feign.OPCHFeign
 *  com.sap.b1.tcli.observer.feign.OPDFFeign
 *  com.sap.b1.tcli.observer.feign.OPDNFeign
 *  com.sap.b1.tcli.observer.feign.OPDTFeign
 *  com.sap.b1.tcli.observer.feign.OPHAFeign
 *  com.sap.b1.tcli.observer.feign.OPIDFeign
 *  com.sap.b1.tcli.observer.feign.OPKGFeign
 *  com.sap.b1.tcli.observer.feign.OPKLFeign
 *  com.sap.b1.tcli.observer.feign.OPLNFeign
 *  com.sap.b1.tcli.observer.feign.OPMGFeign
 *  com.sap.b1.tcli.observer.feign.OPORFeign
 *  com.sap.b1.tcli.observer.feign.OPQTFeign
 *  com.sap.b1.tcli.observer.feign.OPRJFeign
 *  com.sap.b1.tcli.observer.feign.OPRQFeign
 *  com.sap.b1.tcli.observer.feign.OPRRFeign
 *  com.sap.b1.tcli.observer.feign.OPRTFeign
 *  com.sap.b1.tcli.observer.feign.OPSTFeign
 *  com.sap.b1.tcli.observer.feign.OPTRFeign
 *  com.sap.b1.tcli.observer.feign.OPYBFeign
 *  com.sap.b1.tcli.observer.feign.OPYMFeign
 *  com.sap.b1.tcli.observer.feign.OQAGFeign
 *  com.sap.b1.tcli.observer.feign.OQCNFeign
 *  com.sap.b1.tcli.observer.feign.OQFDFeign
 *  com.sap.b1.tcli.observer.feign.OQUEFeign
 *  com.sap.b1.tcli.observer.feign.OQUTFeign
 *  com.sap.b1.tcli.observer.feign.ORCIFeign
 *  com.sap.b1.tcli.observer.feign.ORCLFeign
 *  com.sap.b1.tcli.observer.feign.ORCPFeign
 *  com.sap.b1.tcli.observer.feign.ORCRFeign
 *  com.sap.b1.tcli.observer.feign.ORCTFeign
 *  com.sap.b1.tcli.observer.feign.ORDNFeign
 *  com.sap.b1.tcli.observer.feign.ORDRFeign
 *  com.sap.b1.tcli.observer.feign.ORECFeign
 *  com.sap.b1.tcli.observer.feign.ORINFeign
 *  com.sap.b1.tcli.observer.feign.ORLDFeign
 *  com.sap.b1.tcli.observer.feign.ORPCFeign
 *  com.sap.b1.tcli.observer.feign.ORPDFeign
 *  com.sap.b1.tcli.observer.feign.ORRRFeign
 *  com.sap.b1.tcli.observer.feign.ORSBFeign
 *  com.sap.b1.tcli.observer.feign.ORSCFeign
 *  com.sap.b1.tcli.observer.feign.ORSTFeign
 *  com.sap.b1.tcli.observer.feign.ORTTFeign
 *  com.sap.b1.tcli.observer.feign.OSCLFeign
 *  com.sap.b1.tcli.observer.feign.OSCNFeign
 *  com.sap.b1.tcli.observer.feign.OSCOFeign
 *  com.sap.b1.tcli.observer.feign.OSCPFeign
 *  com.sap.b1.tcli.observer.feign.OSCSFeign
 *  com.sap.b1.tcli.observer.feign.OSCTFeign
 *  com.sap.b1.tcli.observer.feign.OSDAFeign
 *  com.sap.b1.tcli.observer.feign.OSDCFeign
 *  com.sap.b1.tcli.observer.feign.OSHPFeign
 *  com.sap.b1.tcli.observer.feign.OSLPFeign
 *  com.sap.b1.tcli.observer.feign.OSLTFeign
 *  com.sap.b1.tcli.observer.feign.OSSTFeign
 *  com.sap.b1.tcli.observer.feign.OSWAFeign
 *  com.sap.b1.tcli.observer.feign.OTCNFeign
 *  com.sap.b1.tcli.observer.feign.OTERFeign
 *  com.sap.b1.tcli.observer.feign.OTIZFeign
 *  com.sap.b1.tcli.observer.feign.OTNLFeign
 *  com.sap.b1.tcli.observer.feign.OTPLFeign
 *  com.sap.b1.tcli.observer.feign.OTRCFeign
 *  com.sap.b1.tcli.observer.feign.OTRSSFeign
 *  com.sap.b1.tcli.observer.feign.OTRTFeign
 *  com.sap.b1.tcli.observer.feign.OTSHFeign
 *  com.sap.b1.tcli.observer.feign.OUDOFeign
 *  com.sap.b1.tcli.observer.feign.OUGPFeign
 *  com.sap.b1.tcli.observer.feign.OUKDFeign
 *  com.sap.b1.tcli.observer.feign.OUOMFeign
 *  com.sap.b1.tcli.observer.feign.OUQRFeign
 *  com.sap.b1.tcli.observer.feign.OUSRFeign
 *  com.sap.b1.tcli.observer.feign.OUTBFeign
 *  com.sap.b1.tcli.observer.feign.OVEBFeign
 *  com.sap.b1.tcli.observer.feign.OVETFeign
 *  com.sap.b1.tcli.observer.feign.OVPMFeign
 *  com.sap.b1.tcli.observer.feign.OVTGFeign
 *  com.sap.b1.tcli.observer.feign.OWDDFeign
 *  com.sap.b1.tcli.observer.feign.OWGTFeign
 *  com.sap.b1.tcli.observer.feign.OWHSFeign
 *  com.sap.b1.tcli.observer.feign.OWHTFeign
 *  com.sap.b1.tcli.observer.feign.OWLBTFeign
 *  com.sap.b1.tcli.observer.feign.OWLPDFeign
 *  com.sap.b1.tcli.observer.feign.OWNOTFeign
 *  com.sap.b1.tcli.observer.feign.OWORFeign
 *  com.sap.b1.tcli.observer.feign.OWSTFeign
 *  com.sap.b1.tcli.observer.feign.OWSVFeign
 *  com.sap.b1.tcli.observer.feign.OWSVSFeign
 *  com.sap.b1.tcli.observer.feign.OWTMFeign
 *  com.sap.b1.tcli.observer.feign.OWTQFeign
 *  com.sap.b1.tcli.observer.feign.OWTRFeign
 *  com.sap.b1.tcli.observer.feign.OWUACFeign
 *  com.sap.b1.tcli.observer.feign.OWXTFeign
 *  com.sap.b1.tcli.observer.feign.RelationshipMapFeign
 *  com.sap.b1.tcli.observer.feign.UDV1Feign
 *  com.sap.b1.tcli.observer.feign.UVE1Feign
 *  com.sap.b1.tcli.observer.feign.WTD3Feign
 *  com.sap.b1.util.HttpClientPoolUtil
 *  com.sap.b1.web.core.FeignConfigObserver
 *  com.sap.b1.web.core.FeignConfigObserverDecoder
 *  com.sap.b1.web.core.FeignConfigObserverEncoder
 *  com.sap.b1.web.core.FeignConfigSvclInterceptor
 *  feign.Client
 *  feign.Feign
 *  feign.Feign$Builder
 *  feign.Logger
 *  feign.Logger$Level
 *  feign.Request$Options
 *  feign.RequestInterceptor
 *  feign.Retryer
 *  feign.codec.Decoder
 *  feign.codec.Encoder
 *  feign.codec.ErrorDecoder
 *  feign.hc5.ApacheHttp5Client
 *  feign.slf4j.Slf4jLogger
 *  org.apache.hc.client5.http.classic.HttpClient
 *  org.apache.hc.client5.http.impl.classic.CloseableHttpClient
 *  org.springframework.beans.factory.annotation.Autowired
 *  org.springframework.beans.factory.annotation.Value
 *  org.springframework.context.annotation.Bean
 *  org.springframework.context.annotation.Configuration
 */
package com.sap.b1.web.core;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.sap.b1.svcl.client.utils.ServiceLayerErrorDecoder;
import com.sap.b1.tcli.observer.feign.ADM1Feign;
import com.sap.b1.tcli.observer.feign.AddressFeign;
import com.sap.b1.tcli.observer.feign.CUDVFeign;
import com.sap.b1.tcli.observer.feign.CUFDFeign;
import com.sap.b1.tcli.observer.feign.CUVEFeign;
import com.sap.b1.tcli.observer.feign.CUVFFeign;
import com.sap.b1.tcli.observer.feign.DSC1Feign;
import com.sap.b1.tcli.observer.feign.MessageFeign;
import com.sap.b1.tcli.observer.feign.OAARFeign;
import com.sap.b1.tcli.observer.feign.OACGFeign;
import com.sap.b1.tcli.observer.feign.OACPFeign;
import com.sap.b1.tcli.observer.feign.OACTFeign;
import com.sap.b1.tcli.observer.feign.OADFFeign;
import com.sap.b1.tcli.observer.feign.OADMFeign;
import com.sap.b1.tcli.observer.feign.OADPFeign;
import com.sap.b1.tcli.observer.feign.OALCFeign;
import com.sap.b1.tcli.observer.feign.OALIFeign;
import com.sap.b1.tcli.observer.feign.OARGFeign;
import com.sap.b1.tcli.observer.feign.OASGFeign;
import com.sap.b1.tcli.observer.feign.OATCFeign;
import com.sap.b1.tcli.observer.feign.OBCAFeign;
import com.sap.b1.tcli.observer.feign.OBINFeign;
import com.sap.b1.tcli.observer.feign.OBNIFeign;
import com.sap.b1.tcli.observer.feign.OBPLFeign;
import com.sap.b1.tcli.observer.feign.OBPPFeign;
import com.sap.b1.tcli.observer.feign.OBSIFeign;
import com.sap.b1.tcli.observer.feign.OBSJFeign;
import com.sap.b1.tcli.observer.feign.OBTDFeign;
import com.sap.b1.tcli.observer.feign.OBTFFeign;
import com.sap.b1.tcli.observer.feign.OCBIFeign;
import com.sap.b1.tcli.observer.feign.OCDCFeign;
import com.sap.b1.tcli.observer.feign.OCDTFeign;
import com.sap.b1.tcli.observer.feign.OCIFFeign;
import com.sap.b1.tcli.observer.feign.OCLAFeign;
import com.sap.b1.tcli.observer.feign.OCLGFeign;
import com.sap.b1.tcli.observer.feign.OCLOFeign;
import com.sap.b1.tcli.observer.feign.OCLSFeign;
import com.sap.b1.tcli.observer.feign.OCLTFeign;
import com.sap.b1.tcli.observer.feign.OCMTFeign;
import com.sap.b1.tcli.observer.feign.OCNAFeign;
import com.sap.b1.tcli.observer.feign.OCOGFeign;
import com.sap.b1.tcli.observer.feign.OCPNFeign;
import com.sap.b1.tcli.observer.feign.OCQGFeign;
import com.sap.b1.tcli.observer.feign.OCRCFeign;
import com.sap.b1.tcli.observer.feign.OCRDFeign;
import com.sap.b1.tcli.observer.feign.OCRGFeign;
import com.sap.b1.tcli.observer.feign.OCRNFeign;
import com.sap.b1.tcli.observer.feign.OCRPFeign;
import com.sap.b1.tcli.observer.feign.OCRYFeign;
import com.sap.b1.tcli.observer.feign.OCSTFeign;
import com.sap.b1.tcli.observer.feign.OCTGFeign;
import com.sap.b1.tcli.observer.feign.OCTRFeign;
import com.sap.b1.tcli.observer.feign.OCTTFeign;
import com.sap.b1.tcli.observer.feign.OCYCFeign;
import com.sap.b1.tcli.observer.feign.ODCIFeign;
import com.sap.b1.tcli.observer.feign.ODDGFeign;
import com.sap.b1.tcli.observer.feign.ODGLFeign;
import com.sap.b1.tcli.observer.feign.ODGPFeign;
import com.sap.b1.tcli.observer.feign.ODLNFeign;
import com.sap.b1.tcli.observer.feign.ODPIFeign;
import com.sap.b1.tcli.observer.feign.ODPOFeign;
import com.sap.b1.tcli.observer.feign.ODPSFeign;
import com.sap.b1.tcli.observer.feign.ODPTFeign;
import com.sap.b1.tcli.observer.feign.ODRFFeign;
import com.sap.b1.tcli.observer.feign.ODSCFeign;
import com.sap.b1.tcli.observer.feign.ODUNFeign;
import com.sap.b1.tcli.observer.feign.OEDGFeign;
import com.sap.b1.tcli.observer.feign.OEFCFeign;
import com.sap.b1.tcli.observer.feign.OEGPFeign;
import com.sap.b1.tcli.observer.feign.OEMLFeign;
import com.sap.b1.tcli.observer.feign.OEOTFeign;
import com.sap.b1.tcli.observer.feign.OEPEFeign;
import com.sap.b1.tcli.observer.feign.OETCFeign;
import com.sap.b1.tcli.observer.feign.OEXDFeign;
import com.sap.b1.tcli.observer.feign.OFCTFeign;
import com.sap.b1.tcli.observer.feign.OFPRFeign;
import com.sap.b1.tcli.observer.feign.OGDRFeign;
import com.sap.b1.tcli.observer.feign.OHEMFeign;
import com.sap.b1.tcli.observer.feign.OICDFeign;
import com.sap.b1.tcli.observer.feign.OIDCFeign;
import com.sap.b1.tcli.observer.feign.OIDXFeign;
import com.sap.b1.tcli.observer.feign.OIGEFeign;
import com.sap.b1.tcli.observer.feign.OIGEFeign2;
import com.sap.b1.tcli.observer.feign.OIGNFeign;
import com.sap.b1.tcli.observer.feign.OIGNFeign2;
import com.sap.b1.tcli.observer.feign.OINCFeign;
import com.sap.b1.tcli.observer.feign.OINSFeign;
import com.sap.b1.tcli.observer.feign.OINVFeign;
import com.sap.b1.tcli.observer.feign.OIPFFeign;
import com.sap.b1.tcli.observer.feign.OIQIFeign;
import com.sap.b1.tcli.observer.feign.OIQRFeign;
import com.sap.b1.tcli.observer.feign.OISWFeign;
import com.sap.b1.tcli.observer.feign.OITBFeign;
import com.sap.b1.tcli.observer.feign.OITGFeign;
import com.sap.b1.tcli.observer.feign.OITMFeign;
import com.sap.b1.tcli.observer.feign.OITTFeign;
import com.sap.b1.tcli.observer.feign.OJDTFeign;
import com.sap.b1.tcli.observer.feign.OLCTFeign;
import com.sap.b1.tcli.observer.feign.OLGTFeign;
import com.sap.b1.tcli.observer.feign.OLNGFeign;
import com.sap.b1.tcli.observer.feign.OMLTFeign;
import com.sap.b1.tcli.observer.feign.OMPFFeign;
import com.sap.b1.tcli.observer.feign.OMRCFeign;
import com.sap.b1.tcli.observer.feign.OOATFeign;
import com.sap.b1.tcli.observer.feign.OOCRFeign;
import com.sap.b1.tcli.observer.feign.OOFRFeign;
import com.sap.b1.tcli.observer.feign.OOINFeign;
import com.sap.b1.tcli.observer.feign.OOIRFeign;
import com.sap.b1.tcli.observer.feign.OONDFeign;
import com.sap.b1.tcli.observer.feign.OOPBFeign;
import com.sap.b1.tcli.observer.feign.OORLFeign;
import com.sap.b1.tcli.observer.feign.OOSRFeign;
import com.sap.b1.tcli.observer.feign.OOSTFeign;
import com.sap.b1.tcli.observer.feign.OPCHFeign;
import com.sap.b1.tcli.observer.feign.OPDFFeign;
import com.sap.b1.tcli.observer.feign.OPDNFeign;
import com.sap.b1.tcli.observer.feign.OPDTFeign;
import com.sap.b1.tcli.observer.feign.OPHAFeign;
import com.sap.b1.tcli.observer.feign.OPIDFeign;
import com.sap.b1.tcli.observer.feign.OPKGFeign;
import com.sap.b1.tcli.observer.feign.OPKLFeign;
import com.sap.b1.tcli.observer.feign.OPLNFeign;
import com.sap.b1.tcli.observer.feign.OPMGFeign;
import com.sap.b1.tcli.observer.feign.OPORFeign;
import com.sap.b1.tcli.observer.feign.OPQTFeign;
import com.sap.b1.tcli.observer.feign.OPRJFeign;
import com.sap.b1.tcli.observer.feign.OPRQFeign;
import com.sap.b1.tcli.observer.feign.OPRRFeign;
import com.sap.b1.tcli.observer.feign.OPRTFeign;
import com.sap.b1.tcli.observer.feign.OPSTFeign;
import com.sap.b1.tcli.observer.feign.OPTRFeign;
import com.sap.b1.tcli.observer.feign.OPYBFeign;
import com.sap.b1.tcli.observer.feign.OPYMFeign;
import com.sap.b1.tcli.observer.feign.OQAGFeign;
import com.sap.b1.tcli.observer.feign.OQCNFeign;
import com.sap.b1.tcli.observer.feign.OQFDFeign;
import com.sap.b1.tcli.observer.feign.OQUEFeign;
import com.sap.b1.tcli.observer.feign.OQUTFeign;
import com.sap.b1.tcli.observer.feign.ORCIFeign;
import com.sap.b1.tcli.observer.feign.ORCLFeign;
import com.sap.b1.tcli.observer.feign.ORCPFeign;
import com.sap.b1.tcli.observer.feign.ORCRFeign;
import com.sap.b1.tcli.observer.feign.ORCTFeign;
import com.sap.b1.tcli.observer.feign.ORDNFeign;
import com.sap.b1.tcli.observer.feign.ORDRFeign;
import com.sap.b1.tcli.observer.feign.ORECFeign;
import com.sap.b1.tcli.observer.feign.ORINFeign;
import com.sap.b1.tcli.observer.feign.ORLDFeign;
import com.sap.b1.tcli.observer.feign.ORPCFeign;
import com.sap.b1.tcli.observer.feign.ORPDFeign;
import com.sap.b1.tcli.observer.feign.ORRRFeign;
import com.sap.b1.tcli.observer.feign.ORSBFeign;
import com.sap.b1.tcli.observer.feign.ORSCFeign;
import com.sap.b1.tcli.observer.feign.ORSTFeign;
import com.sap.b1.tcli.observer.feign.ORTTFeign;
import com.sap.b1.tcli.observer.feign.OSCLFeign;
import com.sap.b1.tcli.observer.feign.OSCNFeign;
import com.sap.b1.tcli.observer.feign.OSCOFeign;
import com.sap.b1.tcli.observer.feign.OSCPFeign;
import com.sap.b1.tcli.observer.feign.OSCSFeign;
import com.sap.b1.tcli.observer.feign.OSCTFeign;
import com.sap.b1.tcli.observer.feign.OSDAFeign;
import com.sap.b1.tcli.observer.feign.OSDCFeign;
import com.sap.b1.tcli.observer.feign.OSHPFeign;
import com.sap.b1.tcli.observer.feign.OSLPFeign;
import com.sap.b1.tcli.observer.feign.OSLTFeign;
import com.sap.b1.tcli.observer.feign.OSSTFeign;
import com.sap.b1.tcli.observer.feign.OSWAFeign;
import com.sap.b1.tcli.observer.feign.OTCNFeign;
import com.sap.b1.tcli.observer.feign.OTERFeign;
import com.sap.b1.tcli.observer.feign.OTIZFeign;
import com.sap.b1.tcli.observer.feign.OTNLFeign;
import com.sap.b1.tcli.observer.feign.OTPLFeign;
import com.sap.b1.tcli.observer.feign.OTRCFeign;
import com.sap.b1.tcli.observer.feign.OTRSSFeign;
import com.sap.b1.tcli.observer.feign.OTRTFeign;
import com.sap.b1.tcli.observer.feign.OTSHFeign;
import com.sap.b1.tcli.observer.feign.OUDOFeign;
import com.sap.b1.tcli.observer.feign.OUGPFeign;
import com.sap.b1.tcli.observer.feign.OUKDFeign;
import com.sap.b1.tcli.observer.feign.OUOMFeign;
import com.sap.b1.tcli.observer.feign.OUQRFeign;
import com.sap.b1.tcli.observer.feign.OUSRFeign;
import com.sap.b1.tcli.observer.feign.OUTBFeign;
import com.sap.b1.tcli.observer.feign.OVEBFeign;
import com.sap.b1.tcli.observer.feign.OVETFeign;
import com.sap.b1.tcli.observer.feign.OVPMFeign;
import com.sap.b1.tcli.observer.feign.OVTGFeign;
import com.sap.b1.tcli.observer.feign.OWDDFeign;
import com.sap.b1.tcli.observer.feign.OWGTFeign;
import com.sap.b1.tcli.observer.feign.OWHSFeign;
import com.sap.b1.tcli.observer.feign.OWHTFeign;
import com.sap.b1.tcli.observer.feign.OWLBTFeign;
import com.sap.b1.tcli.observer.feign.OWLPDFeign;
import com.sap.b1.tcli.observer.feign.OWNOTFeign;
import com.sap.b1.tcli.observer.feign.OWORFeign;
import com.sap.b1.tcli.observer.feign.OWSTFeign;
import com.sap.b1.tcli.observer.feign.OWSVFeign;
import com.sap.b1.tcli.observer.feign.OWSVSFeign;
import com.sap.b1.tcli.observer.feign.OWTMFeign;
import com.sap.b1.tcli.observer.feign.OWTQFeign;
import com.sap.b1.tcli.observer.feign.OWTRFeign;
import com.sap.b1.tcli.observer.feign.OWUACFeign;
import com.sap.b1.tcli.observer.feign.OWXTFeign;
import com.sap.b1.tcli.observer.feign.RelationshipMapFeign;
import com.sap.b1.tcli.observer.feign.UDV1Feign;
import com.sap.b1.tcli.observer.feign.UVE1Feign;
import com.sap.b1.tcli.observer.feign.WTD3Feign;
import com.sap.b1.util.HttpClientPoolUtil;
import com.sap.b1.web.core.FeignConfigObserverDecoder;
import com.sap.b1.web.core.FeignConfigObserverEncoder;
import com.sap.b1.web.core.FeignConfigSvclInterceptor;
import feign.Client;
import feign.Feign;
import feign.Logger;
import feign.Request;
import feign.RequestInterceptor;
import feign.Retryer;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.hc5.ApacheHttp5Client;
import feign.slf4j.Slf4jLogger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;
import org.apache.hc.client5.http.classic.HttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfigObserver {
    private static final String B1S_WEBCLIENT_URL = "/b1s/v1/webclient";
    Feign.Builder builder;
    @Value(value="${b1_svcl_url}")
    String svclUrl;
    @Value(value="${feign.client.config.default.loggerLevel:NONE}")
    Logger.Level logLevel;
    @Value(value="${feign.client.config.OINCFeign.loggerLevel:NONE}")
    Logger.Level oincLogLevel;
    @Value(value="${b1.changepath-api-timeout:600}")
    int apiTimeout;
    @Autowired
    FeignConfigSvclInterceptor feignConfigSvclInterceptor;

    @Autowired
    public void init() throws Exception {
        CloseableHttpClient httpClients = HttpClientPoolUtil.buildSDKClient();
        JsonMapper.Builder jsonBuilder = JsonMapper.builder();
        jsonBuilder.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
        ObjectMapper mapper = jsonBuilder.build();
        SimpleModule module = new SimpleModule();
        mapper.registerModule((Module)module);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");
        mapper.setDateFormat((DateFormat)df);
        this.builder = this.getFeignBuilder().client((Client)new ApacheHttp5Client((HttpClient)httpClients)).logger((Logger)new Slf4jLogger()).retryer(Retryer.NEVER_RETRY).options(new Request.Options(30L, TimeUnit.SECONDS, (long)this.apiTimeout, TimeUnit.SECONDS, true)).decoder((Decoder)new FeignConfigObserverDecoder(mapper)).encoder((Encoder)new FeignConfigObserverEncoder(mapper)).errorDecoder((ErrorDecoder)new ServiceLayerErrorDecoder());
        this.builder.requestInterceptor((RequestInterceptor)this.feignConfigSvclInterceptor);
    }

    Feign.Builder getFeignBuilder() {
        return Feign.builder();
    }

    <T> T target(Class<T> clazz) {
        return (T)this.builder.logLevel(this.logLevel).target(clazz, this.svclUrl + B1S_WEBCLIENT_URL);
    }

    @Bean
    OINCFeign initOINCObsFeign() {
        return (OINCFeign)this.builder.logLevel(this.oincLogLevel).target(OINCFeign.class, this.svclUrl + B1S_WEBCLIENT_URL);
    }

    @Bean
    OUSRFeign initOUSRObsFeign() {
        return (OUSRFeign)this.target(OUSRFeign.class);
    }

    @Bean
    OCRDFeign initOCRDObsFeign() {
        return (OCRDFeign)this.target(OCRDFeign.class);
    }

    @Bean
    OCTGFeign initOCTGObsFeign() {
        return (OCTGFeign)this.target(OCTGFeign.class);
    }

    @Bean
    OITMFeign initOITMObsFeign() {
        return (OITMFeign)this.target(OITMFeign.class);
    }

    @Bean
    ORDRFeign initORDRObsFeign() {
        return (ORDRFeign)this.target(ORDRFeign.class);
    }

    @Bean
    OQUTFeign initOQUTObsFeign() {
        return (OQUTFeign)this.target(OQUTFeign.class);
    }

    @Bean
    ODLNFeign initODLNObsFeign() {
        return (ODLNFeign)this.target(ODLNFeign.class);
    }

    @Bean
    ORRRFeign initORRRObsFeign() {
        return (ORRRFeign)this.target(ORRRFeign.class);
    }

    @Bean
    OPORFeign initOPORObsFeign() {
        return (OPORFeign)this.target(OPORFeign.class);
    }

    @Bean
    ODPIFeign initODPIObsFeign() {
        return (ODPIFeign)this.target(ODPIFeign.class);
    }

    @Bean
    ODPOFeign initODPOObsFeign() {
        return (ODPOFeign)this.target(ODPOFeign.class);
    }

    @Bean
    ORINFeign initORINObsFeign() {
        return (ORINFeign)this.target(ORINFeign.class);
    }

    @Bean
    ORDNFeign initORDNObsFeign() {
        return (ORDNFeign)this.target(ORDNFeign.class);
    }

    @Bean
    OINVFeign initOINVObsFeign() {
        return (OINVFeign)this.target(OINVFeign.class);
    }

    @Bean
    OCLGFeign initOCLGObsFeign() {
        return (OCLGFeign)this.target(OCLGFeign.class);
    }

    @Bean
    OVPMFeign initOVPMObsFeign() {
        return (OVPMFeign)this.target(OVPMFeign.class);
    }

    @Bean
    ORCTFeign initORCTObsFeign() {
        return (ORCTFeign)this.target(ORCTFeign.class);
    }

    @Bean
    OPDFFeign initOPDFObsFeign() {
        return (OPDFFeign)this.target(OPDFFeign.class);
    }

    @Bean
    OHEMFeign initOHEMObsFeign() {
        return (OHEMFeign)this.target(OHEMFeign.class);
    }

    @Bean
    CUFDFeign initCUFDObsFeign() {
        return (CUFDFeign)this.target(CUFDFeign.class);
    }

    @Bean
    OSCLFeign initOSCLObsFeign() {
        return (OSCLFeign)this.target(OSCLFeign.class);
    }

    @Bean
    OSCNFeign initOSCNObsFeign() {
        return (OSCNFeign)this.target(OSCNFeign.class);
    }

    @Bean
    ODRFFeign initODRFObsFeign() {
        return (ODRFFeign)this.target(ODRFFeign.class);
    }

    @Bean
    OWUACFeign initOWUACObsFeign() {
        return (OWUACFeign)this.target(OWUACFeign.class);
    }

    @Bean
    OWLPDFeign initOWLPDObsFeign() {
        return (OWLPDFeign)this.target(OWLPDFeign.class);
    }

    @Bean
    OWLBTFeign initOWLBTObsFeign() {
        return (OWLBTFeign)this.target(OWLBTFeign.class);
    }

    @Bean
    OWNOTFeign initOWNOTObsFeign() {
        return (OWNOTFeign)this.target(OWNOTFeign.class);
    }

    @Bean
    OSDAFeign initOSDAObsFeign() {
        return (OSDAFeign)this.target(OSDAFeign.class);
    }

    @Bean
    OPQTFeign initOPQTObsFeign() {
        return (OPQTFeign)this.target(OPQTFeign.class);
    }

    @Bean
    OMPFFeign initOMPFFeign() {
        return (OMPFFeign)this.target(OMPFFeign.class);
    }

    @Bean
    OSLTFeign initOSLTFeign() {
        return (OSLTFeign)this.target(OSLTFeign.class);
    }

    @Bean
    OOATFeign initOOATFeign() {
        return (OOATFeign)this.target(OOATFeign.class);
    }

    @Bean
    OTSHFeign initOTSHFeign() {
        return (OTSHFeign)this.target(OTSHFeign.class);
    }

    @Bean
    OJDTFeign initOJDTFeign() {
        return (OJDTFeign)this.target(OJDTFeign.class);
    }

    @Bean
    OUQRFeign initOUQRFeign() {
        return (OUQRFeign)this.target(OUQRFeign.class);
    }

    @Bean
    OWSVFeign initOWSVFeign() {
        return (OWSVFeign)this.target(OWSVFeign.class);
    }

    @Bean
    OWSVSFeign initOWSVSFeign() {
        return (OWSVSFeign)this.target(OWSVSFeign.class);
    }

    @Bean
    OINSFeign initOINSFeign() {
        return (OINSFeign)this.target(OINSFeign.class);
    }

    @Bean
    OPDNFeign initOPDNObsFeign() {
        return (OPDNFeign)this.target(OPDNFeign.class);
    }

    @Bean
    ORPDFeign initORPDObsFeign() {
        return (ORPDFeign)this.target(ORPDFeign.class);
    }

    @Bean
    OQFDFeign initOQFDObsFeign() {
        return (OQFDFeign)this.target(OQFDFeign.class);
    }

    @Bean
    OPCHFeign initOPCHObsFeign() {
        return (OPCHFeign)this.target(OPCHFeign.class);
    }

    @Bean
    OACTFeign initOACTObsFeign() {
        return (OACTFeign)this.target(OACTFeign.class);
    }

    @Bean
    OCRYFeign initOCRYObsFeign() {
        return (OCRYFeign)this.target(OCRYFeign.class);
    }

    @Bean
    OADFFeign initOADFObsFeign() {
        return (OADFFeign)this.target(OADFFeign.class);
    }

    @Bean
    OTIZFeign initOTIZObsFeign() {
        return (OTIZFeign)this.target(OTIZFeign.class);
    }

    @Bean
    ORPCFeign initORPCObsFeign() {
        return (ORPCFeign)this.target(ORPCFeign.class);
    }

    @Bean
    OIGNFeign initOIGNObsFeign() {
        return (OIGNFeign)this.target(OIGNFeign.class);
    }

    @Bean
    OIGNFeign2 initOIGNObsFeign2() {
        return (OIGNFeign2)this.target(OIGNFeign2.class);
    }

    @Bean
    OIGEFeign initOIGEObsFeign() {
        return (OIGEFeign)this.target(OIGEFeign.class);
    }

    @Bean
    OIGEFeign2 initOIGEObsFeign2() {
        return (OIGEFeign2)this.target(OIGEFeign2.class);
    }

    @Bean
    OWTRFeign initOWTRObsFeign() {
        return (OWTRFeign)this.target(OWTRFeign.class);
    }

    @Bean
    OWTQFeign initOWTQObsFeign() {
        return (OWTQFeign)this.target(OWTQFeign.class);
    }

    @Bean
    OWORFeign initOWORObsFeign() {
        return (OWORFeign)this.target(OWORFeign.class);
    }

    @Bean
    OITTFeign initOITTObsFeign() {
        return (OITTFeign)this.target(OITTFeign.class);
    }

    @Bean
    OCPNFeign initOCPNObsFeign() {
        return (OCPNFeign)this.target(OCPNFeign.class);
    }

    @Bean
    ODPSFeign initODPSObsFeign() {
        return (ODPSFeign)this.target(ODPSFeign.class);
    }

    @Bean
    OBINFeign initOBINObsFeign() {
        return (OBINFeign)this.target(OBINFeign.class);
    }

    @Bean
    ORSCFeign initORSCObsFeign() {
        return (ORSCFeign)this.target(ORSCFeign.class);
    }

    @Bean
    OCTRFeign initOCTRObsFeign() {
        return (OCTRFeign)this.target(OCTRFeign.class);
    }

    @Bean
    OPMGFeign initOPMGObsFeign() {
        return (OPMGFeign)this.target(OPMGFeign.class);
    }

    @Bean
    OPHAFeign initOPHAObsFeign() {
        return (OPHAFeign)this.target(OPHAFeign.class);
    }

    @Bean
    OWHSFeign initOWHSObsFeign() {
        return (OWHSFeign)this.target(OWHSFeign.class);
    }

    @Bean
    OIQRFeign initOIQRObsFeign() {
        return (OIQRFeign)this.target(OIQRFeign.class);
    }

    @Bean
    OIQIFeign initOIQIObsFeign() {
        return (OIQIFeign)this.target(OIQIFeign.class);
    }

    @Bean
    OPLNFeign initOPLNObsFeign() {
        return (OPLNFeign)this.target(OPLNFeign.class);
    }

    @Bean
    OEDGFeign initOEDGObsFeign() {
        return (OEDGFeign)this.target(OEDGFeign.class);
    }

    @Bean
    OSLPFeign initOSLPObsFeign() {
        return (OSLPFeign)this.target(OSLPFeign.class);
    }

    @Bean
    OBCAFeign initOBCAObsFeign() {
        return (OBCAFeign)this.target(OBCAFeign.class);
    }

    @Bean
    OBPPFeign initOBPPObsFeign() {
        return (OBPPFeign)this.target(OBPPFeign.class);
    }

    @Bean
    OCLOFeign initOCLOObsFeign() {
        return (OCLOFeign)this.target(OCLOFeign.class);
    }

    @Bean
    OCLSFeign initOCLSObsFeign() {
        return (OCLSFeign)this.target(OCLSFeign.class);
    }

    @Bean
    OCLTFeign initOCLTObsFeign() {
        return (OCLTFeign)this.target(OCLTFeign.class);
    }

    @Bean
    OCMTFeign initOCMTObsFeign() {
        return (OCMTFeign)this.target(OCMTFeign.class);
    }

    @Bean
    OCOGFeign initOCOGObsFeign() {
        return (OCOGFeign)this.target(OCOGFeign.class);
    }

    @Bean
    OCRCFeign initOCRCObsFeign() {
        return (OCRCFeign)this.target(OCRCFeign.class);
    }

    @Bean
    OCDTFeign initOCDTObsFeign() {
        return (OCDTFeign)this.target(OCDTFeign.class);
    }

    @Bean
    OCRGFeign initOCRGObsFeign() {
        return (OCRGFeign)this.target(OCRGFeign.class);
    }

    @Bean
    OCRNFeign initOCRNObsFeign() {
        return (OCRNFeign)this.target(OCRNFeign.class);
    }

    @Bean
    OCRPFeign initOCRPObsFeign() {
        return (OCRPFeign)this.target(OCRPFeign.class);
    }

    @Bean
    ODUNFeign initODUNObsFeign() {
        return (ODUNFeign)this.target(ODUNFeign.class);
    }

    @Bean
    OEGPFeign initOEGPObsFeign() {
        return (OEGPFeign)this.target(OEGPFeign.class);
    }

    @Bean
    OIDCFeign initOIDCObsFeign() {
        return (OIDCFeign)this.target(OIDCFeign.class);
    }

    @Bean
    OLNGFeign initOLNGObsFeign() {
        return (OLNGFeign)this.target(OLNGFeign.class);
    }

    @Bean
    OOFRFeign initOOFRObsFeign() {
        return (OOFRFeign)this.target(OOFRFeign.class);
    }

    @Bean
    OOINFeign initOOINObsFeign() {
        return (OOINFeign)this.target(OOINFeign.class);
    }

    @Bean
    OOIRFeign initOOIRObsFeign() {
        return (OOIRFeign)this.target(OOIRFeign.class);
    }

    @Bean
    OONDFeign initOONDObsFeign() {
        return (OONDFeign)this.target(OONDFeign.class);
    }

    @Bean
    OORLFeign initOORLObsFeign() {
        return (OORLFeign)this.target(OORLFeign.class);
    }

    @Bean
    OOSRFeign initOOSRObsFeign() {
        return (OOSRFeign)this.target(OOSRFeign.class);
    }

    @Bean
    OOSTFeign initOOSTObsFeign() {
        return (OOSTFeign)this.target(OOSTFeign.class);
    }

    @Bean
    OPRJFeign initOPRJObsFeign() {
        return (OPRJFeign)this.target(OPRJFeign.class);
    }

    @Bean
    OPRTFeign initOPRTObsFeign() {
        return (OPRTFeign)this.target(OPRTFeign.class);
    }

    @Bean
    OPSTFeign initOPSTObsFeign() {
        return (OPSTFeign)this.target(OPSTFeign.class);
    }

    @Bean
    OPYBFeign initOPYBObsFeign() {
        return (OPYBFeign)this.target(OPYBFeign.class);
    }

    @Bean
    OSCOFeign initOSCOObsFeign() {
        return (OSCOFeign)this.target(OSCOFeign.class);
    }

    @Bean
    OSCPFeign initOSCPObsFeign() {
        return (OSCPFeign)this.target(OSCPFeign.class);
    }

    @Bean
    OSCSFeign initOSCSObsFeign() {
        return (OSCSFeign)this.target(OSCSFeign.class);
    }

    @Bean
    OSCTFeign initOSCTObsFeign() {
        return (OSCTFeign)this.target(OSCTFeign.class);
    }

    @Bean
    OSHPFeign initOSHPObsFeign() {
        return (OSHPFeign)this.target(OSHPFeign.class);
    }

    @Bean
    OSSTFeign initOSSTObsFeign() {
        return (OSSTFeign)this.target(OSSTFeign.class);
    }

    @Bean
    OUOMFeign initOUOMObsFeign() {
        return (OUOMFeign)this.target(OUOMFeign.class);
    }

    @Bean
    OWDDFeign initOWDDObsFeign() {
        return (OWDDFeign)this.target(OWDDFeign.class);
    }

    @Bean
    OWSTFeign initOWSTObsFeign() {
        return (OWSTFeign)this.target(OWSTFeign.class);
    }

    @Bean
    OWTMFeign initOWTMObsFeign() {
        return (OWTMFeign)this.target(OWTMFeign.class);
    }

    @Bean
    OEMLFeign initOEMLObsFeign() {
        return (OEMLFeign)this.target(OEMLFeign.class);
    }

    @Bean
    OATCFeign initOATCObsFeign() {
        return (OATCFeign)this.target(OATCFeign.class);
    }

    @Bean
    OFCTFeign initOFCTObsFeign() {
        return (OFCTFeign)this.target(OFCTFeign.class);
    }

    @Bean
    OTRCFeign initOTRCObsFeign() {
        return (OTRCFeign)this.target(OTRCFeign.class);
    }

    @Bean
    OTRTFeign initOTRTObsFeign() {
        return (OTRTFeign)this.target(OTRTFeign.class);
    }

    @Bean
    ORCRFeign initORCRObsFeign() {
        return (ORCRFeign)this.target(ORCRFeign.class);
    }

    @Bean
    ODPTFeign initODPTObsFeign() {
        return (ODPTFeign)this.target(ODPTFeign.class);
    }

    @Bean
    OPDTFeign initOPDTObsFeign() {
        return (OPDTFeign)this.target(OPDTFeign.class);
    }

    @Bean
    OVTGFeign initOVTGObsFeign() {
        return (OVTGFeign)this.target(OVTGFeign.class);
    }

    @Bean
    OWHTFeign initOWHTObsFeign() {
        return (OWHTFeign)this.target(OWHTFeign.class);
    }

    @Bean
    OPYMFeign initOPYMObsFeign() {
        return (OPYMFeign)this.target(OPYMFeign.class);
    }

    @Bean
    OCTTFeign initOCTTObsFeign() {
        return (OCTTFeign)this.target(OCTTFeign.class);
    }

    @Bean
    OQCNFeign initOQCNObsFeign() {
        return (OQCNFeign)this.target(OQCNFeign.class);
    }

    @Bean
    OQAGFeign initOQAGObsFeign() {
        return (OQAGFeign)this.target(OQAGFeign.class);
    }

    @Bean
    OMRCFeign initOMRCObsFeign() {
        return (OMRCFeign)this.target(OMRCFeign.class);
    }

    @Bean
    OLCTFeign initOLCTObsFeign() {
        return (OLCTFeign)this.target(OLCTFeign.class);
    }

    @Bean
    OITGFeign initOITGObsFeign() {
        return (OITGFeign)this.target(OITGFeign.class);
    }

    @Bean
    OIDXFeign initOIDXObsFeign() {
        return (OIDXFeign)this.target(OIDXFeign.class);
    }

    @Bean
    OLGTFeign initOLGTObsFeign() {
        return (OLGTFeign)this.target(OLGTFeign.class);
    }

    @Bean
    OARGFeign initOARGObsFeign() {
        return (OARGFeign)this.target(OARGFeign.class);
    }

    @Bean
    OWGTFeign initOWGTObsFeign() {
        return (OWGTFeign)this.target(OWGTFeign.class);
    }

    @Bean
    OCQGFeign initOCQGObsFeign() {
        return (OCQGFeign)this.target(OCQGFeign.class);
    }

    @Bean
    AddressFeign initAddressFeign() {
        return (AddressFeign)this.target(AddressFeign.class);
    }

    @Bean
    OALIFeign initOALIObsFeign() {
        return (OALIFeign)this.target(OALIFeign.class);
    }

    @Bean
    OPRQFeign initOPRQObsFeign() {
        return (OPRQFeign)this.target(OPRQFeign.class);
    }

    @Bean
    OPRRFeign initOPRRObsFeign() {
        return (OPRRFeign)this.target(OPRRFeign.class);
    }

    @Bean
    OADMFeign initOADMObsFeign() {
        return (OADMFeign)this.target(OADMFeign.class);
    }

    @Bean
    ORSTFeign initORSTObsFeign() {
        return (ORSTFeign)this.target(ORSTFeign.class);
    }

    @Bean
    OITBFeign initOITBObsFeign() {
        return (OITBFeign)this.target(OITBFeign.class);
    }

    @Bean
    ORSBFeign initORSBObsFeign() {
        return (ORSBFeign)this.target(ORSBFeign.class);
    }

    @Bean
    OCSTFeign initOCSTObsFeign() {
        return (OCSTFeign)this.target(OCSTFeign.class);
    }

    @Bean
    RelationshipMapFeign initRelationshipMapFeign() {
        return (RelationshipMapFeign)this.target(RelationshipMapFeign.class);
    }

    @Bean
    OTNLFeign initOTNLObsFeign() {
        return (OTNLFeign)this.target(OTNLFeign.class);
    }

    @Bean
    OTCNFeign initOTCNObsFeign() {
        return (OTCNFeign)this.target(OTCNFeign.class);
    }

    @Bean
    OUGPFeign initOUGPObsFeign() {
        return (OUGPFeign)this.target(OUGPFeign.class);
    }

    @Bean
    OCYCFeign initOCYCObsFeign() {
        return (OCYCFeign)this.target(OCYCFeign.class);
    }

    @Bean
    OCDCFeign initOCDCObsFeign() {
        return (OCDCFeign)this.target(OCDCFeign.class);
    }

    @Bean
    OCLAFeign initOCLAObsFeign() {
        return (OCLAFeign)this.target(OCLAFeign.class);
    }

    @Bean
    ODSCFeign initODSCObsFeign() {
        return (ODSCFeign)this.target(ODSCFeign.class);
    }

    @Bean
    OPKGFeign initOPKGObsFeign() {
        return (OPKGFeign)this.target(OPKGFeign.class);
    }

    @Bean
    OEXDFeign initOEXDObsFeign() {
        return (OEXDFeign)this.target(OEXDFeign.class);
    }

    @Bean
    ORLDFeign initORLDObsFeign() {
        return (ORLDFeign)this.target(ORLDFeign.class);
    }

    @Bean
    OUKDFeign initOUKDObsFeign() {
        return (OUKDFeign)this.target(OUKDFeign.class);
    }

    @Bean
    OUTBFeign initOUTBObsFeign() {
        return (OUTBFeign)this.target(OUTBFeign.class);
    }

    @Bean
    OPIDFeign initOPIDObsFeign() {
        return (OPIDFeign)this.target(OPIDFeign.class);
    }

    @Bean
    DSC1Feign initDSC1ObsFeign() {
        return (DSC1Feign)this.target(DSC1Feign.class);
    }

    @Bean
    OFPRFeign initOFPRObsFeign() {
        return (OFPRFeign)this.target(OFPRFeign.class);
    }

    @Bean
    OOPBFeign initOOPBObsFeign() {
        return (OOPBFeign)this.target(OOPBFeign.class);
    }

    @Bean
    OBNIFeign initOBNIFeign() {
        return (OBNIFeign)this.target(OBNIFeign.class);
    }

    @Bean
    OBSIFeign initOBSIFeign() {
        return (OBSIFeign)this.target(OBSIFeign.class);
    }

    @Bean
    OCBIFeign initOCBIFeign() {
        return (OCBIFeign)this.target(OCBIFeign.class);
    }

    @Bean
    OCNAFeign initOCNAFeign() {
        return (OCNAFeign)this.target(OCNAFeign.class);
    }

    @Bean
    OTRSSFeign initOTRSSFeign() {
        return (OTRSSFeign)this.target(OTRSSFeign.class);
    }

    @Bean
    OADPFeign initOADPObsFeign() {
        return (OADPFeign)this.target(OADPFeign.class);
    }

    @Bean
    OTERFeign initOTERObsFeign() {
        return (OTERFeign)this.target(OTERFeign.class);
    }

    @Bean
    OAARFeign initOAARObsFeign() {
        return (OAARFeign)this.target(OAARFeign.class);
    }

    @Bean
    CUVFFeign initCUVFFeign() {
        return (CUVFFeign)this.target(CUVFFeign.class);
    }

    @Bean
    CUDVFeign initCUDVFeign() {
        return (CUDVFeign)this.target(CUDVFeign.class);
    }

    @Bean
    OACPFeign initOACPObsFeign() {
        return (OACPFeign)this.target(OACPFeign.class);
    }

    @Bean
    UDV1Feign initUDV1Feign() {
        return (UDV1Feign)this.target(UDV1Feign.class);
    }

    @Bean
    CUVEFeign initCUVEFeign() {
        return (CUVEFeign)this.target(CUVEFeign.class);
    }

    @Bean
    UVE1Feign initUVE1Feign() {
        return (UVE1Feign)this.target(UVE1Feign.class);
    }

    @Bean
    OUDOFeign initOUDOObsFeign() {
        return (OUDOFeign)this.target(OUDOFeign.class);
    }

    @Bean
    OICDFeign initOICDFeignObsFeign() {
        return (OICDFeign)this.target(OICDFeign.class);
    }

    @Bean
    ORECFeign initORECObsFeign() {
        return (ORECFeign)this.target(ORECFeign.class);
    }

    @Bean
    OSDCFeign initOSDCObsFeign() {
        return (OSDCFeign)this.target(OSDCFeign.class);
    }

    @Bean
    OEOTFeign initOEOTObsFeign() {
        return (OEOTFeign)this.target(OEOTFeign.class);
    }

    @Bean
    OEFCFeign initOEFCObsFeign() {
        return (OEFCFeign)this.target(OEFCFeign.class);
    }

    @Bean
    OBPLFeign initOBPLObsFeign() {
        return (OBPLFeign)this.target(OBPLFeign.class);
    }

    @Bean
    OBTFFeign initOBTFObsFeign() {
        return (OBTFFeign)this.target(OBTFFeign.class);
    }

    @Bean
    OEPEFeign initOEPEObsFeign() {
        return (OEPEFeign)this.target(OEPEFeign.class);
    }

    @Bean
    OBTDFeign initOBTDObsFeign() {
        return (OBTDFeign)this.target(OBTDFeign.class);
    }

    @Bean
    OETCFeign initOETCObsFeign() {
        return (OETCFeign)this.target(OETCFeign.class);
    }

    @Bean
    ODGLFeign initODGLObsFeign() {
        return (ODGLFeign)this.target(ODGLFeign.class);
    }

    @Bean
    ODDGFeign initODDGObsFeign() {
        return (ODDGFeign)this.target(ODDGFeign.class);
    }

    @Bean
    OALCFeign initOALCFeign() {
        return (OALCFeign)this.target(OALCFeign.class);
    }

    @Bean
    OSWAFeign initOSWAObsFeign() {
        return (OSWAFeign)this.target(OSWAFeign.class);
    }

    @Bean
    OPTRFeign initOPTRObsFeign() {
        return (OPTRFeign)this.target(OPTRFeign.class);
    }

    @Bean
    OWXTFeign initOWXTObsFeign() {
        return (OWXTFeign)this.target(OWXTFeign.class);
    }

    @Bean
    OQUEFeign initOQUEObsFeign() {
        return (OQUEFeign)this.target(OQUEFeign.class);
    }

    @Bean
    OVEBFeign initOVEBObsFeign() {
        return (OVEBFeign)this.target(OVEBFeign.class);
    }

    @Bean
    OVETFeign initOVETObsFeign() {
        return (OVETFeign)this.target(OVETFeign.class);
    }

    @Bean
    OGDRFeign initOGDRObsFeign() {
        return (OGDRFeign)this.target(OGDRFeign.class);
    }

    @Bean
    OTPLFeign initOTPLObsFeign() {
        return (OTPLFeign)this.target(OTPLFeign.class);
    }

    @Bean
    ORCLFeign initORCLObsFeign() {
        return (ORCLFeign)this.target(ORCLFeign.class);
    }

    @Bean
    ORCPFeign initORCPObsFeign() {
        return (ORCPFeign)this.target(ORCPFeign.class);
    }

    @Bean
    OIPFFeign initOIPFObsFeign() {
        return (OIPFFeign)this.target(OIPFFeign.class);
    }

    @Bean
    ODCIFeign initODCIObsFeign() {
        return (ODCIFeign)this.target(ODCIFeign.class);
    }

    @Bean
    ADM1Feign initADM1ObsFeign() {
        return (ADM1Feign)this.target(ADM1Feign.class);
    }

    @Bean
    OCIFFeign initOCIFObsFeign() {
        return (OCIFFeign)this.target(OCIFFeign.class);
    }

    @Bean
    OMLTFeign initOMLTObsFeign() {
        return (OMLTFeign)this.target(OMLTFeign.class);
    }

    @Bean
    ORCIFeign initORCIObsFeign() {
        return (ORCIFeign)this.target(ORCIFeign.class);
    }

    @Bean
    MessageFeign initMessageFeign() {
        return (MessageFeign)this.target(MessageFeign.class);
    }

    @Bean
    OACGFeign initOACGObsFeign() {
        return (OACGFeign)this.target(OACGFeign.class);
    }

    @Bean
    OISWFeign initOISWObsFeign() {
        return (OISWFeign)this.target(OISWFeign.class);
    }

    @Bean
    OOCRFeign initOOCRObsFeign() {
        return (OOCRFeign)this.target(OOCRFeign.class);
    }

    @Bean
    ORTTFeign initORTTObsFeign() {
        return (ORTTFeign)this.target(ORTTFeign.class);
    }

    @Bean
    OBSJFeign initOBSJObsFeign() {
        return (OBSJFeign)this.target(OBSJFeign.class);
    }

    @Bean
    OPKLFeign initOPKLObsFeign() {
        return (OPKLFeign)this.target(OPKLFeign.class);
    }

    @Bean
    OASGFeign initOASGObsFeign() {
        return (OASGFeign)this.target(OASGFeign.class);
    }

    @Bean
    ODGPFeign initODGPObsFeign() {
        return (ODGPFeign)this.target(ODGPFeign.class);
    }

    @Bean
    WTD3Feign initWTD3ObsFeign() {
        return (WTD3Feign)this.target(WTD3Feign.class);
    }
}

