export interface IFixedAssetCategory {
  id?: number;
  categoryCode?: string;
  categoryName?: string;
  categoryDescription?: string;
  depreciationRegimeId?: number;
}

export class FixedAssetCategory implements IFixedAssetCategory {
  constructor(
    public id?: number,
    public categoryCode?: string,
    public categoryName?: string,
    public categoryDescription?: string,
    public depreciationRegimeId?: number
  ) {}
}
