package cn.digitalpublishing.facade;

import cn.digitalpublishing.dao.BConfigurationDao;
import cn.digitalpublishing.dao.BInstitutionDao;
import cn.digitalpublishing.dao.BIpRangeDao;
import cn.digitalpublishing.dao.BLanguageDao;
import cn.digitalpublishing.dao.BPDACounterDao;
import cn.digitalpublishing.dao.BPDAInfoDao;
import cn.digitalpublishing.dao.BPublisherDao;
import cn.digitalpublishing.dao.BServiceDao;
import cn.digitalpublishing.dao.BSourceDao;
import cn.digitalpublishing.dao.BSubjectDao;
import cn.digitalpublishing.dao.BTokenDao;
import cn.digitalpublishing.dao.BUrlDao;
import cn.digitalpublishing.dao.BUuRelationDao;
import cn.digitalpublishing.dao.CAccountDao;
import cn.digitalpublishing.dao.CAlertsSendedQueueDao;
import cn.digitalpublishing.dao.CAlertsWaitingQueueDao;
import cn.digitalpublishing.dao.CDirectoryDao;
import cn.digitalpublishing.dao.CFavouritesDao;
import cn.digitalpublishing.dao.CSearchHisDao;
import cn.digitalpublishing.dao.CUserAlertsDao;
import cn.digitalpublishing.dao.CUserDao;
import cn.digitalpublishing.dao.CUserPropDao;
import cn.digitalpublishing.dao.CUserTypeDao;
import cn.digitalpublishing.dao.CUserTypePropDao;
import cn.digitalpublishing.dao.LAccessDao;
import cn.digitalpublishing.dao.LComplicatingDao;
import cn.digitalpublishing.dao.LLicenseDao;
import cn.digitalpublishing.dao.LLicenseIpDao;
import cn.digitalpublishing.dao.LUserAlertsLogDao;
import cn.digitalpublishing.dao.MMarkDataDao;
import cn.digitalpublishing.dao.OAdvertisementDao;
import cn.digitalpublishing.dao.OCurrencyDao;
import cn.digitalpublishing.dao.OExchangeDao;
import cn.digitalpublishing.dao.OOrderDao;
import cn.digitalpublishing.dao.OOrderDetailDao;
import cn.digitalpublishing.dao.OTransationDao;
import cn.digitalpublishing.dao.PCcRelationDao;
import cn.digitalpublishing.dao.PCollectionDao;
import cn.digitalpublishing.dao.PContentRelationDao;
import cn.digitalpublishing.dao.PCsRelationDao;
import cn.digitalpublishing.dao.PNewsDao;
import cn.digitalpublishing.dao.PNoteDao;
import cn.digitalpublishing.dao.PPageDao;
import cn.digitalpublishing.dao.PPriceDao;
import cn.digitalpublishing.dao.PPriceTypeDao;
import cn.digitalpublishing.dao.PPublicationsDao;
import cn.digitalpublishing.dao.PPublicationsRelationDao;
import cn.digitalpublishing.dao.PRecordDao;
import cn.digitalpublishing.dao.PRelatedPublisherDao;
import cn.digitalpublishing.dao.PriceTypeDao;
import cn.digitalpublishing.dao.RRecommendDao;
import cn.digitalpublishing.dao.RRecommendDetailDao;
import cn.digitalpublishing.dao.RecommendDao;
import cn.digitalpublishing.dao.ResourcesSumDao;
import cn.digitalpublishing.dao.SOnsaleDao;
import cn.digitalpublishing.dao.SSupplierDao;
import cn.digitalpublishing.dao.UPRelationDao;
import cn.digitalpublishing.dao.UPaymentDao;

public class DaoFacade {
	ResourcesSumDao resourcesSumDao;
	UPaymentDao uPaymentDao;
	UPRelationDao upRelationDao;
	LComplicatingDao lComplicatingDao;
	BConfigurationDao bConfigurationDao;
	BInstitutionDao bInstitutionDao;
	BIpRangeDao bIpRangeDao;
	BLanguageDao bLanguageDao;
	BPublisherDao bPublisherDao;
	BSubjectDao bSubjectDao;
	CFavouritesDao cFavouritesDao;
	CAccountDao cAccountDao;

	CUserAlertsDao cUserAlertsDao;
	CAlertsWaitingQueueDao alertsWaitingQueueDao;
	CAlertsSendedQueueDao alertsSendedQueueDao;
	CUserDao cUserDao;
	CUserPropDao cUserPropDao;
	CUserTypeDao cUserTypeDao;
	CUserTypePropDao cUserTypePropDao;

	OCurrencyDao oCurrencyDao;
	OExchangeDao oExchangeDao;
	OOrderDao oOrderDao;
	OOrderDetailDao oOrderDetailDao;
	OTransationDao oTransationDao;
	PCcRelationDao pCcRelationDao;
	PCollectionDao pCollectionDao;
	PCsRelationDao pCsRelationDao;
	PNoteDao pNoteDao;
	PPageDao pPageDao;
	PPriceDao pPriceDao;
	PPriceTypeDao pPriceTypeDao;
	PPublicationsDao pPublicationsDao;
	PRecordDao pRecordDao;
	RRecommendDao rRecommendDao;
	RRecommendDetailDao rRecommendDetailDao;
	LLicenseDao lLicenseDao;
	LLicenseIpDao lLicenseIpDao;

	CDirectoryDao cDirectoryDao;
	CSearchHisDao cSearchHisDao;

	MMarkDataDao mMarkDataDao;
	LAccessDao lAccessDao;

	PriceTypeDao priceTypeDao;
	LUserAlertsLogDao lUserAlertsLogDao;
	BUuRelationDao uuRelationDao;
	BUrlDao urlDao;

	BPDAInfoDao bPDAInfoDao;
	BPDACounterDao bPDACounterDao;

	PPublicationsRelationDao pPublicationsRelationDao;
	RecommendDao recommendDao;
	OAdvertisementDao oAdvertisementDao;
	BServiceDao bServiceDao;
	PNewsDao pNewsDao;
	PRelatedPublisherDao pRelatedPublisherDao;
	/**
	 * 上线统计Dao
	 */
	private SOnsaleDao sOnsaleDao;
	/**
	 * 统计表
	 */
	private SSupplierDao suppDao;
	/**
	 * 供应商o
	 */
	private BSourceDao bSourceDao;
	
	private PContentRelationDao pContentRelationDao;
	
	BTokenDao bTokenDao;

	public BTokenDao getbTokenDao() {
		return bTokenDao;
	}

	public void setbTokenDao(BTokenDao bTokenDao) {
		this.bTokenDao = bTokenDao;
	}

	public PContentRelationDao getpContentRelationDao() {
		return pContentRelationDao;
	}

	public void setpContentRelationDao(PContentRelationDao pContentRelationDao) {
		this.pContentRelationDao = pContentRelationDao;
	}

	public BSourceDao getbSourceDao() {
		return bSourceDao;
	}

	public void setbSourceDao(BSourceDao bSourceDao) {
		this.bSourceDao = bSourceDao;
	}


	public SSupplierDao getSuppDao() {
		return suppDao;
	}

	public void setSuppDao(SSupplierDao suppDao) {
		this.suppDao = suppDao;
	}

	public BServiceDao getbServiceDao() {
		return bServiceDao;
	}

	public void setbServiceDao(BServiceDao bServiceDao) {
		this.bServiceDao = bServiceDao;
	}

	public UPRelationDao getUpRelationDao() {
		return upRelationDao;
	}

	public void setUpRelationDao(UPRelationDao upRelationDao) {
		this.upRelationDao = upRelationDao;
	}

	public LComplicatingDao getlComplicatingDao() {
		return lComplicatingDao;
	}

	public void setlComplicatingDao(LComplicatingDao lComplicatingDao) {
		this.lComplicatingDao = lComplicatingDao;
	}

	public LAccessDao getlAccessDao() {
		return lAccessDao;
	}

	public void setlAccessDao(LAccessDao lAccessDao) {
		this.lAccessDao = lAccessDao;
	}

	public CSearchHisDao getcSearchHisDao() {
		return cSearchHisDao;
	}

	public void setcSearchHisDao(CSearchHisDao cSearchHisDao) {
		this.cSearchHisDao = cSearchHisDao;
	}

	public CDirectoryDao getcDirectoryDao() {
		return cDirectoryDao;
	}

	public void setcDirectoryDao(CDirectoryDao cDirectoryDao) {
		this.cDirectoryDao = cDirectoryDao;
	}

	public OCurrencyDao getoCurrencyDao() {
		return oCurrencyDao;
	}

	public void setoCurrencyDao(OCurrencyDao oCurrencyDao) {
		this.oCurrencyDao = oCurrencyDao;
	}

	public OExchangeDao getoExchangeDao() {
		return oExchangeDao;
	}

	public void setoExchangeDao(OExchangeDao oExchangeDao) {
		this.oExchangeDao = oExchangeDao;
	}

	public OOrderDao getoOrderDao() {
		return oOrderDao;
	}

	public void setoOrderDao(OOrderDao oOrderDao) {
		this.oOrderDao = oOrderDao;
	}

	public OOrderDetailDao getoOrderDetailDao() {
		return oOrderDetailDao;
	}

	public void setoOrderDetailDao(OOrderDetailDao oOrderDetailDao) {
		this.oOrderDetailDao = oOrderDetailDao;
	}

	public OTransationDao getoTransationDao() {
		return oTransationDao;
	}

	public void setoTransationDao(OTransationDao oTransationDao) {
		this.oTransationDao = oTransationDao;
	}

	public PCcRelationDao getpCcRelationDao() {
		return pCcRelationDao;
	}

	public void setpCcRelationDao(PCcRelationDao pCcRelationDao) {
		this.pCcRelationDao = pCcRelationDao;
	}

	public PCollectionDao getpCollectionDao() {
		return pCollectionDao;
	}

	public void setpCollectionDao(PCollectionDao pCollectionDao) {
		this.pCollectionDao = pCollectionDao;
	}

	public PCsRelationDao getpCsRelationDao() {
		return pCsRelationDao;
	}

	public void setpCsRelationDao(PCsRelationDao pCsRelationDao) {
		this.pCsRelationDao = pCsRelationDao;
	}

	public PNoteDao getpNoteDao() {
		return pNoteDao;
	}

	public void setpNoteDao(PNoteDao pNoteDao) {
		this.pNoteDao = pNoteDao;
	}

	public PPageDao getpPageDao() {
		return pPageDao;
	}

	public void setpPageDao(PPageDao pPageDao) {
		this.pPageDao = pPageDao;
	}

	public PPriceDao getpPriceDao() {
		return pPriceDao;
	}

	public void setpPriceDao(PPriceDao pPriceDao) {
		this.pPriceDao = pPriceDao;
	}

	public PPriceTypeDao getpPriceTypeDao() {
		return pPriceTypeDao;
	}

	public void setpPriceTypeDao(PPriceTypeDao pPriceTypeDao) {
		this.pPriceTypeDao = pPriceTypeDao;
	}

	public PPublicationsDao getpPublicationsDao() {
		return pPublicationsDao;
	}

	public void setpPublicationsDao(PPublicationsDao pPublicationsDao) {
		this.pPublicationsDao = pPublicationsDao;
	}

	public PRecordDao getpRecordDao() {
		return pRecordDao;
	}

	public void setpRecordDao(PRecordDao pRecordDao) {
		this.pRecordDao = pRecordDao;
	}

	public RRecommendDao getrRecommendDao() {
		return rRecommendDao;
	}

	public void setrRecommendDao(RRecommendDao rRecommendDao) {
		this.rRecommendDao = rRecommendDao;
	}

	public RRecommendDetailDao getrRecommendDetailDao() {
		return rRecommendDetailDao;
	}

	public void setrRecommendDetailDao(RRecommendDetailDao rRecommendDetailDao) {
		this.rRecommendDetailDao = rRecommendDetailDao;
	}

	public LLicenseDao getlLicenseDao() {
		return lLicenseDao;
	}

	public void setlLicenseDao(LLicenseDao lLicenseDao) {
		this.lLicenseDao = lLicenseDao;
	}

	public LLicenseIpDao getlLicenseIpDao() {
		return lLicenseIpDao;
	}

	public void setlLicenseIpDao(LLicenseIpDao lLicenseIpDao) {
		this.lLicenseIpDao = lLicenseIpDao;
	}

	public BConfigurationDao getbConfigurationDao() {
		return bConfigurationDao;
	}

	public void setbConfigurationDao(BConfigurationDao bConfigurationDao) {
		this.bConfigurationDao = bConfigurationDao;
	}

	public BInstitutionDao getbInstitutionDao() {
		return bInstitutionDao;
	}

	public void setbInstitutionDao(BInstitutionDao bInstitutionDao) {
		this.bInstitutionDao = bInstitutionDao;
	}

	public BIpRangeDao getbIpRangeDao() {
		return bIpRangeDao;
	}

	public void setbIpRangeDao(BIpRangeDao bIpRangeDao) {
		this.bIpRangeDao = bIpRangeDao;
	}

	public BLanguageDao getbLanguageDao() {
		return bLanguageDao;
	}

	public void setbLanguageDao(BLanguageDao bLanguageDao) {
		this.bLanguageDao = bLanguageDao;
	}

	public BPublisherDao getbPublisherDao() {
		return bPublisherDao;
	}

	public void setbPublisherDao(BPublisherDao bPublisherDao) {
		this.bPublisherDao = bPublisherDao;
	}

	public BSubjectDao getbSubjectDao() {
		return bSubjectDao;
	}

	public void setbSubjectDao(BSubjectDao bSubjectDao) {
		this.bSubjectDao = bSubjectDao;
	}

	public CFavouritesDao getcFavouritesDao() {
		return cFavouritesDao;
	}

	public void setcFavouritesDao(CFavouritesDao cFavouritesDao) {
		this.cFavouritesDao = cFavouritesDao;
	}

	public CUserDao getcUserDao() {
		return cUserDao;
	}

	public void setcUserDao(CUserDao cUserDao) {
		this.cUserDao = cUserDao;
	}

	public CAccountDao getcAccountDao() {
		return cAccountDao;
	}

	public void setcAccountDao(CAccountDao cAccountDao) {
		this.cAccountDao = cAccountDao;
	}

	public CUserTypeDao getcUserTypeDao() {
		return cUserTypeDao;
	}

	public void setcUserTypeDao(CUserTypeDao cUserTypeDao) {
		this.cUserTypeDao = cUserTypeDao;
	}

	public CUserTypePropDao getcUserTypePropDao() {
		return cUserTypePropDao;
	}

	public void setcUserTypePropDao(CUserTypePropDao cUserTypePropDao) {
		this.cUserTypePropDao = cUserTypePropDao;
	}

	public CUserPropDao getCUserPropDao() {
		return cUserPropDao;
	}

	public void setCUserPropDao(CUserPropDao userPropDao) {
		cUserPropDao = userPropDao;
	}

	public CUserAlertsDao getCUserAlertsDao() {
		return cUserAlertsDao;
	}

	public void setCUserAlertsDao(CUserAlertsDao userAlertsDao) {
		cUserAlertsDao = userAlertsDao;
	}

	public CAlertsWaitingQueueDao getAlertsWaitingQueueDao() {
		return alertsWaitingQueueDao;
	}

	public void setAlertsWaitingQueueDao(
			CAlertsWaitingQueueDao alertsWaitingQueueDao) {
		this.alertsWaitingQueueDao = alertsWaitingQueueDao;
	}

	public CAlertsSendedQueueDao getAlertsSendedQueueDao() {
		return alertsSendedQueueDao;
	}

	public void setAlertsSendedQueueDao(
			CAlertsSendedQueueDao alertsSendedQueueDao) {
		this.alertsSendedQueueDao = alertsSendedQueueDao;
	}

	public MMarkDataDao getMMarkDataDao() {
		return mMarkDataDao;
	}

	public void setMMarkDataDao(MMarkDataDao markDataDao) {
		mMarkDataDao = markDataDao;
	}

	public PriceTypeDao getPriceTypeDao() {
		return priceTypeDao;
	}

	public void setPriceTypeDao(PriceTypeDao priceTypeDao) {
		this.priceTypeDao = priceTypeDao;
	}

	public CUserTypeDao getCUserTypeDao() {
		return cUserTypeDao;
	}

	public LUserAlertsLogDao getlUserAlertsLogDao() {
		return lUserAlertsLogDao;
	}

	public void setlUserAlertsLogDao(LUserAlertsLogDao lUserAlertsLogDao) {
		this.lUserAlertsLogDao = lUserAlertsLogDao;
	}

	public LComplicatingDao getLComplicatingDao() {
		return lComplicatingDao;
	}

	public void setLComplicatingDao(LComplicatingDao complicatingDao) {
		lComplicatingDao = complicatingDao;
	}

	public BConfigurationDao getBConfigurationDao() {
		return bConfigurationDao;
	}

	public void setBConfigurationDao(BConfigurationDao configurationDao) {
		bConfigurationDao = configurationDao;
	}

	public BInstitutionDao getBInstitutionDao() {
		return bInstitutionDao;
	}

	public void setBInstitutionDao(BInstitutionDao institutionDao) {
		bInstitutionDao = institutionDao;
	}

	public BIpRangeDao getBIpRangeDao() {
		return bIpRangeDao;
	}

	public void setBIpRangeDao(BIpRangeDao ipRangeDao) {
		bIpRangeDao = ipRangeDao;
	}

	public BLanguageDao getBLanguageDao() {
		return bLanguageDao;
	}

	public void setBLanguageDao(BLanguageDao languageDao) {
		bLanguageDao = languageDao;
	}

	public BPublisherDao getBPublisherDao() {
		return bPublisherDao;
	}

	public void setBPublisherDao(BPublisherDao publisherDao) {
		bPublisherDao = publisherDao;
	}

	public BSubjectDao getBSubjectDao() {
		return bSubjectDao;
	}

	public void setBSubjectDao(BSubjectDao subjectDao) {
		bSubjectDao = subjectDao;
	}

	public CFavouritesDao getCFavouritesDao() {
		return cFavouritesDao;
	}

	public void setCFavouritesDao(CFavouritesDao favouritesDao) {
		cFavouritesDao = favouritesDao;
	}

	public CAccountDao getCAccountDao() {
		return cAccountDao;
	}

	public void setCAccountDao(CAccountDao accountDao) {
		cAccountDao = accountDao;
	}

	public CUserDao getCUserDao() {
		return cUserDao;
	}

	public void setCUserDao(CUserDao userDao) {
		cUserDao = userDao;
	}

	public CUserTypePropDao getCUserTypePropDao() {
		return cUserTypePropDao;
	}

	public void setCUserTypePropDao(CUserTypePropDao userTypePropDao) {
		cUserTypePropDao = userTypePropDao;
	}

	public OCurrencyDao getOCurrencyDao() {
		return oCurrencyDao;
	}

	public void setOCurrencyDao(OCurrencyDao currencyDao) {
		oCurrencyDao = currencyDao;
	}

	public OExchangeDao getOExchangeDao() {
		return oExchangeDao;
	}

	public void setOExchangeDao(OExchangeDao exchangeDao) {
		oExchangeDao = exchangeDao;
	}

	public OOrderDao getOOrderDao() {
		return oOrderDao;
	}

	public void setOOrderDao(OOrderDao orderDao) {
		oOrderDao = orderDao;
	}

	public OOrderDetailDao getOOrderDetailDao() {
		return oOrderDetailDao;
	}

	public void setOOrderDetailDao(OOrderDetailDao orderDetailDao) {
		oOrderDetailDao = orderDetailDao;
	}

	public OTransationDao getOTransationDao() {
		return oTransationDao;
	}

	public void setOTransationDao(OTransationDao transationDao) {
		oTransationDao = transationDao;
	}

	public PCcRelationDao getPCcRelationDao() {
		return pCcRelationDao;
	}

	public void setPCcRelationDao(PCcRelationDao ccRelationDao) {
		pCcRelationDao = ccRelationDao;
	}

	public PCollectionDao getPCollectionDao() {
		return pCollectionDao;
	}

	public void setPCollectionDao(PCollectionDao collectionDao) {
		pCollectionDao = collectionDao;
	}

	public PCsRelationDao getPCsRelationDao() {
		return pCsRelationDao;
	}

	public void setPCsRelationDao(PCsRelationDao csRelationDao) {
		pCsRelationDao = csRelationDao;
	}

	public PNoteDao getPNoteDao() {
		return pNoteDao;
	}

	public void setPNoteDao(PNoteDao noteDao) {
		pNoteDao = noteDao;
	}

	public PPageDao getPPageDao() {
		return pPageDao;
	}

	public void setPPageDao(PPageDao pageDao) {
		pPageDao = pageDao;
	}

	public PPriceDao getPPriceDao() {
		return pPriceDao;
	}

	public void setPPriceDao(PPriceDao priceDao) {
		pPriceDao = priceDao;
	}

	public PPriceTypeDao getPPriceTypeDao() {
		return pPriceTypeDao;
	}

	public void setPPriceTypeDao(PPriceTypeDao priceTypeDao) {
		pPriceTypeDao = priceTypeDao;
	}

	public PPublicationsDao getPPublicationsDao() {
		return pPublicationsDao;
	}

	public void setPPublicationsDao(PPublicationsDao publicationsDao) {
		pPublicationsDao = publicationsDao;
	}

	public PRecordDao getPRecordDao() {
		return pRecordDao;
	}

	public void setPRecordDao(PRecordDao recordDao) {
		pRecordDao = recordDao;
	}

	public RRecommendDao getRRecommendDao() {
		return rRecommendDao;
	}

	public void setRRecommendDao(RRecommendDao recommendDao) {
		rRecommendDao = recommendDao;
	}

	public RRecommendDetailDao getRRecommendDetailDao() {
		return rRecommendDetailDao;
	}

	public void setRRecommendDetailDao(RRecommendDetailDao recommendDetailDao) {
		rRecommendDetailDao = recommendDetailDao;
	}

	public LLicenseDao getLLicenseDao() {
		return lLicenseDao;
	}

	public void setLLicenseDao(LLicenseDao licenseDao) {
		lLicenseDao = licenseDao;
	}

	public LLicenseIpDao getLLicenseIpDao() {
		return lLicenseIpDao;
	}

	public void setLLicenseIpDao(LLicenseIpDao licenseIpDao) {
		lLicenseIpDao = licenseIpDao;
	}

	public CDirectoryDao getCDirectoryDao() {
		return cDirectoryDao;
	}

	public void setCDirectoryDao(CDirectoryDao directoryDao) {
		cDirectoryDao = directoryDao;
	}

	public CSearchHisDao getCSearchHisDao() {
		return cSearchHisDao;
	}

	public void setCSearchHisDao(CSearchHisDao searchHisDao) {
		cSearchHisDao = searchHisDao;
	}

	public LAccessDao getLAccessDao() {
		return lAccessDao;
	}

	public void setLAccessDao(LAccessDao accessDao) {
		lAccessDao = accessDao;
	}

	public LUserAlertsLogDao getLUserAlertsLogDao() {
		return lUserAlertsLogDao;
	}

	public void setLUserAlertsLogDao(LUserAlertsLogDao userAlertsLogDao) {
		lUserAlertsLogDao = userAlertsLogDao;
	}

	public SOnsaleDao getSOnsaleDao() {
		return sOnsaleDao;
	}

	public void setSOnsaleDao(SOnsaleDao onsaleDao) {
		sOnsaleDao = onsaleDao;
	}

	public void setCUserTypeDao(CUserTypeDao userTypeDao) {
		cUserTypeDao = userTypeDao;
	}

	public UPaymentDao getuPaymentDao() {
		return uPaymentDao;
	}

	public void setuPaymentDao(UPaymentDao uPaymentDao) {
		this.uPaymentDao = uPaymentDao;
	}

	public BUuRelationDao getUuRelationDao() {
		return uuRelationDao;
	}

	public void setUuRelationDao(BUuRelationDao uuRelationDao) {
		this.uuRelationDao = uuRelationDao;
	}

	public BUrlDao getUrlDao() {
		return urlDao;
	}

	public void setUrlDao(BUrlDao urlDao) {
		this.urlDao = urlDao;
	}

	public BPDAInfoDao getbPDAInfoDao() {
		return bPDAInfoDao;
	}

	public void setbPDAInfoDao(BPDAInfoDao bPDAInfoDao) {
		this.bPDAInfoDao = bPDAInfoDao;
	}

	public BPDACounterDao getbPDACounterDao() {
		return bPDACounterDao;
	}

	public void setbPDACounterDao(BPDACounterDao bPDACounterDao) {
		this.bPDACounterDao = bPDACounterDao;
	}

	public ResourcesSumDao getResourcesSumDao() {
		return resourcesSumDao;
	}

	public void setResourcesSumDao(ResourcesSumDao resourcesSumDao) {
		this.resourcesSumDao = resourcesSumDao;
	}

	public PPublicationsRelationDao getpPublicationsRelationDao() {
		return pPublicationsRelationDao;
	}

	public void setpPublicationsRelationDao(
			PPublicationsRelationDao pPublicationsRelationDao) {
		this.pPublicationsRelationDao = pPublicationsRelationDao;
	}

	public RecommendDao getRecommendDao() {
		return recommendDao;
	}

	public void setRecommendDao(RecommendDao recommendDao) {
		this.recommendDao = recommendDao;
	}

	public CUserAlertsDao getcUserAlertsDao() {
		return cUserAlertsDao;
	}

	public void setcUserAlertsDao(CUserAlertsDao cUserAlertsDao) {
		this.cUserAlertsDao = cUserAlertsDao;
	}

	public CUserPropDao getcUserPropDao() {
		return cUserPropDao;
	}

	public void setcUserPropDao(CUserPropDao cUserPropDao) {
		this.cUserPropDao = cUserPropDao;
	}

	public MMarkDataDao getmMarkDataDao() {
		return mMarkDataDao;
	}

	public void setmMarkDataDao(MMarkDataDao mMarkDataDao) {
		this.mMarkDataDao = mMarkDataDao;
	}

	public OAdvertisementDao getoAdvertisementDao() {
		return oAdvertisementDao;
	}

	public void setoAdvertisementDao(OAdvertisementDao oAdvertisementDao) {
		this.oAdvertisementDao = oAdvertisementDao;
	}

	public SOnsaleDao getsOnsaleDao() {
		return sOnsaleDao;
	}

	public void setsOnsaleDao(SOnsaleDao sOnsaleDao) {
		this.sOnsaleDao = sOnsaleDao;
	}

	public PNewsDao getpNewsDao() {
		return pNewsDao;
	}

	public void setpNewsDao(PNewsDao pNewsDao) {
		this.pNewsDao = pNewsDao;
	}

	public PRelatedPublisherDao getpRelatedPublisherDao() {
		return pRelatedPublisherDao;
	}

	public void setpRelatedPublisherDao(PRelatedPublisherDao pRelatedPublisherDao) {
		this.pRelatedPublisherDao = pRelatedPublisherDao;
	}
	
}
