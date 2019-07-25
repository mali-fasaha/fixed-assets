import { Moment } from 'moment';

export interface ICapitalWorkInProgress {
  id?: number;
  transactionMonth?: Moment;
  assetSerialTag?: string;
  serviceOutletCode?: string;
  transactionId?: number;
  transactionDetails?: string;
  transactionAmount?: number;
}

export class CapitalWorkInProgress implements ICapitalWorkInProgress {
  constructor(
    public id?: number,
    public transactionMonth?: Moment,
    public assetSerialTag?: string,
    public serviceOutletCode?: string,
    public transactionId?: number,
    public transactionDetails?: string,
    public transactionAmount?: number
  ) {}
}
