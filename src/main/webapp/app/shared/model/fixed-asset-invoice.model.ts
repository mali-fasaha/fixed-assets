import { Moment } from 'moment';

export interface IFixedAssetInvoice {
  id?: number;
  invoiceReference?: string;
  invoiceDate?: Moment;
  invoiceAmount?: number;
  isProforma?: boolean;
  isCreditNote?: boolean;
  attachmentsContentType?: string;
  attachments?: any;
  dealerDealerName?: string;
  dealerId?: number;
}

export class FixedAssetInvoice implements IFixedAssetInvoice {
  constructor(
    public id?: number,
    public invoiceReference?: string,
    public invoiceDate?: Moment,
    public invoiceAmount?: number,
    public isProforma?: boolean,
    public isCreditNote?: boolean,
    public attachmentsContentType?: string,
    public attachments?: any,
    public dealerDealerName?: string,
    public dealerId?: number
  ) {
    this.isProforma = this.isProforma || false;
    this.isCreditNote = this.isCreditNote || false;
  }
}
