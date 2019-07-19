export const enum AssetDecayType {
  STRAIGHT_LINE = 'STRAIGHT_LINE',
  DECLINING_BALANCE = 'DECLINING_BALANCE'
}

export interface IDepreciationRegime {
  id?: number;
  assetDecayType?: AssetDecayType;
  depreciationRate?: number;
  description?: string;
}

export class DepreciationRegime implements IDepreciationRegime {
  constructor(public id?: number, public assetDecayType?: AssetDecayType, public depreciationRate?: number, public description?: string) {}
}
