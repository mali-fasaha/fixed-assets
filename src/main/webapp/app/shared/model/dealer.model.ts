import { IFixedAssetInvoice } from 'app/shared/model/fixed-asset-invoice.model';

export const enum TitleTypes {
  DR = 'DR',
  ENG = 'ENG',
  HON = 'HON',
  JUST = 'JUST',
  MES = 'MES',
  MISS = 'MISS',
  MMS = 'MMS',
  MR = 'MR',
  MRS = 'MRS',
  MS = 'MS',
  MST = 'MST',
  PROF = 'PROF',
  REV = 'REV',
  CPA = 'CPA'
}

export interface IDealer {
  id?: number;
  title?: TitleTypes;
  dealerName?: string;
  dealerAddress?: string;
  dealerPhoneNumber?: string;
  dealerEmail?: string;
  bankName?: string;
  bankAccountNumber?: string;
  bankBranch?: string;
  bankSwiftCode?: string;
  bankPhysicalAddress?: string;
  domicile?: string;
  taxAuthorityRef?: string;
  fixedAssetInvoices?: IFixedAssetInvoice[];
}

export class Dealer implements IDealer {
  constructor(
    public id?: number,
    public title?: TitleTypes,
    public dealerName?: string,
    public dealerAddress?: string,
    public dealerPhoneNumber?: string,
    public dealerEmail?: string,
    public bankName?: string,
    public bankAccountNumber?: string,
    public bankBranch?: string,
    public bankSwiftCode?: string,
    public bankPhysicalAddress?: string,
    public domicile?: string,
    public taxAuthorityRef?: string,
    public fixedAssetInvoices?: IFixedAssetInvoice[]
  ) {}
}
