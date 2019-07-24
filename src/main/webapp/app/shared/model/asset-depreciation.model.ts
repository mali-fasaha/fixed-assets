import { Moment } from 'moment';

export interface IAssetDepreciation {
  id?: number;
  description?: string;
  depreciationAmount?: number;
  depreciationDate?: Moment;
  categoryId?: number;
  assetItemId?: number;
}

export class AssetDepreciation implements IAssetDepreciation {
  constructor(
    public id?: number,
    public description?: string,
    public depreciationAmount?: number,
    public depreciationDate?: Moment,
    public categoryId?: number,
    public assetItemId?: number
  ) {}
}
