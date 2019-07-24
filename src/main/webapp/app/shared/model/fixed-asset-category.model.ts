export interface IFixedAssetCategory {
  id?: number;
  categoryName?: string;
  categoryDescription?: string;
  categoryAssetCode?: string;
  categoryDepreciationCode?: string;
  depreciationRegimeId?: number;
}

export class FixedAssetCategory implements IFixedAssetCategory {
  constructor(
    public id?: number,
    public categoryName?: string,
    public categoryDescription?: string,
    public categoryAssetCode?: string,
    public categoryDepreciationCode?: string,
    public depreciationRegimeId?: number
  ) {}
}
