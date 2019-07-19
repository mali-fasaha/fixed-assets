import { Moment } from 'moment';

export interface IFixedAssetItem {
  id?: number;
  serviceOutletCode?: string;
  assetCategoryCode?: string;
  assetCategory?: string;
  fixedAssetSerialCode?: string;
  fixedAssetDescription?: string;
  purchaseDate?: Moment;
  purchaseCost?: number;
  purchaseTransactionId?: number;
  ownershipDocumentId?: number;
  assetPictureContentType?: string;
  assetPicture?: any;
}

export class FixedAssetItem implements IFixedAssetItem {
  constructor(
    public id?: number,
    public serviceOutletCode?: string,
    public assetCategoryCode?: string,
    public assetCategory?: string,
    public fixedAssetSerialCode?: string,
    public fixedAssetDescription?: string,
    public purchaseDate?: Moment,
    public purchaseCost?: number,
    public purchaseTransactionId?: number,
    public ownershipDocumentId?: number,
    public assetPictureContentType?: string,
    public assetPicture?: any
  ) {}
}
