import { Moment } from 'moment';

export const enum AssetCondition {
  EXCELLENT = 'EXCELLENT',
  GOOD = 'GOOD',
  VIABLE = 'VIABLE',
  BAD = 'BAD',
  OBSOLETE = 'OBSOLETE',
  WRITE_OFF = 'WRITE_OFF'
}

export interface IFixedAssetAssessment {
  id?: number;
  description?: string;
  assetCondition?: AssetCondition;
  assessmentDate?: Moment;
  assessmentRemarks?: string;
  nameOfAssessingStaff?: string;
  nameOfAssessmentContractor?: string;
  currentServiceOutletCode?: string;
  currentPhysicalAddress?: string;
  nextAssessmentDate?: Moment;
  nameOfUser?: string;
  fixedAssetPictureContentType?: string;
  fixedAssetPicture?: any;
  fixedAssetItemId?: number;
  estimatedValue?: number;
  estimatedUsefulMonths?: number;
}

export class FixedAssetAssessment implements IFixedAssetAssessment {
  constructor(
    public id?: number,
    public description?: string,
    public assetCondition?: AssetCondition,
    public assessmentDate?: Moment,
    public assessmentRemarks?: string,
    public nameOfAssessingStaff?: string,
    public nameOfAssessmentContractor?: string,
    public currentServiceOutletCode?: string,
    public currentPhysicalAddress?: string,
    public nextAssessmentDate?: Moment,
    public nameOfUser?: string,
    public fixedAssetPictureContentType?: string,
    public fixedAssetPicture?: any,
    public fixedAssetItemId?: number,
    public estimatedValue?: number,
    public estimatedUsefulMonths?: number
  ) {}
}
