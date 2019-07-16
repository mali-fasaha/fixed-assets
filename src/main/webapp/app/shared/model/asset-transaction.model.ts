import { Moment } from 'moment';

export interface IAssetTransaction {
  id?: number;
  transactionReference?: string;
  transactionDate?: Moment;
  scannedDocumentId?: number;
}

export class AssetTransaction implements IAssetTransaction {
  constructor(
    public id?: number,
    public transactionReference?: string,
    public transactionDate?: Moment,
    public scannedDocumentId?: number
  ) {}
}
