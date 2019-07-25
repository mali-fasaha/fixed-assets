import { Moment } from 'moment';

export interface IAssetAcquisition {
  id?: number;
  description?: string;
  acquisitionMonth?: Moment;
  assetSerial?: string;
  serviceOutletCode?: string;
  acquisitionTransactionId?: number;
  assetCategoryId?: number;
  purchaseAmount?: number;
  assetDealerId?: number;
  assetInvoiceId?: number;
}

export class AssetAcquisition implements IAssetAcquisition {
  constructor(
    public id?: number,
    public description?: string,
    public acquisitionMonth?: Moment,
    public assetSerial?: string,
    public serviceOutletCode?: string,
    public acquisitionTransactionId?: number,
    public assetCategoryId?: number,
    public purchaseAmount?: number,
    public assetDealerId?: number,
    public assetInvoiceId?: number
  ) {}
}
