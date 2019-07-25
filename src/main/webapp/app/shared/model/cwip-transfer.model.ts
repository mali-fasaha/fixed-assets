import { Moment } from 'moment';

export interface ICwipTransfer {
  id?: number;
  transferMonth?: Moment;
  assetSerialTag?: string;
  serviceOutletCode?: string;
  transferTransactionId?: number;
  assetCategoryId?: number;
  cwipTransactionId?: number;
  transferDetails?: string;
  transferAmount?: number;
  dealerId?: number;
  transactionInvoiceId?: number;
}

export class CwipTransfer implements ICwipTransfer {
  constructor(
    public id?: number,
    public transferMonth?: Moment,
    public assetSerialTag?: string,
    public serviceOutletCode?: string,
    public transferTransactionId?: number,
    public assetCategoryId?: number,
    public cwipTransactionId?: number,
    public transferDetails?: string,
    public transferAmount?: number,
    public dealerId?: number,
    public transactionInvoiceId?: number
  ) {}
}
