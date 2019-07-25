import { Moment } from 'moment';

export interface IAssetDisposal {
  id?: number;
  description?: string;
  disposalDate?: Moment;
  assetCategoryId?: number;
  assetItemId?: number;
  disposalProceeds?: number;
  netBookValue?: number;
  profitOnDisposal?: number;
  scannedDocumentId?: number;
  assetDealerId?: number;
  assetPictureContentType?: string;
  assetPicture?: any;
}

export class AssetDisposal implements IAssetDisposal {
  constructor(
    public id?: number,
    public description?: string,
    public disposalDate?: Moment,
    public assetCategoryId?: number,
    public assetItemId?: number,
    public disposalProceeds?: number,
    public netBookValue?: number,
    public profitOnDisposal?: number,
    public scannedDocumentId?: number,
    public assetDealerId?: number,
    public assetPictureContentType?: string,
    public assetPicture?: any
  ) {}
}
