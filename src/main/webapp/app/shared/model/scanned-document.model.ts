export interface IScannedDocument {
  id?: number;
  description?: string;
  approvalDocumentContentType?: string;
  approvalDocument?: any;
  invoiceDocumentContentType?: string;
  invoiceDocument?: any;
  lpoDocumentContentType?: string;
  lpoDocument?: any;
  requisitionDocumentContentType?: string;
  requisitionDocument?: any;
  otherDocumentsContentType?: string;
  otherDocuments?: any;
}

export class ScannedDocument implements IScannedDocument {
  constructor(
    public id?: number,
    public description?: string,
    public approvalDocumentContentType?: string,
    public approvalDocument?: any,
    public invoiceDocumentContentType?: string,
    public invoiceDocument?: any,
    public lpoDocumentContentType?: string,
    public lpoDocument?: any,
    public requisitionDocumentContentType?: string,
    public requisitionDocument?: any,
    public otherDocumentsContentType?: string,
    public otherDocuments?: any
  ) {}
}
