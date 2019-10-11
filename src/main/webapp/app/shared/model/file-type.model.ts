export const enum FileMediumTypes {
  EXCEL = 'EXCEL',
  PDF = 'PDF',
  POWERPOINT = 'POWERPOINT',
  DOC = 'DOC',
  TEXT = 'TEXT',
  JSON = 'JSON',
  HTML5 = 'HTML5'
}

export const enum FileModelType {
  DEPRECIATION_UPLOAD = 'DEPRECIATION_UPLOAD',
  ACQUISITION_UPLOAD = 'ACQUISITION_UPLOAD',
  DISPOSAL_UPLOAD = 'DISPOSAL_UPLOAD',
  CWIP_UPLOAD = 'CWIP_UPLOAD',
  CWIP_TRANSFER_UPLOAD = 'CWIP_TRANSFER_UPLOAD',
  ASSET_ITEM_UPLOAD = 'ASSET_ITEM_UPLOAD'
}

export interface IFileType {
  id?: number;
  fileTypeName?: string;
  fileMediumType?: FileMediumTypes;
  description?: string;
  fileTemplateContentType?: string;
  fileTemplate?: any;
  fileType?: FileModelType;
}

export class FileType implements IFileType {
  constructor(
    public id?: number,
    public fileTypeName?: string,
    public fileMediumType?: FileMediumTypes,
    public description?: string,
    public fileTemplateContentType?: string,
    public fileTemplate?: any,
    public fileType?: FileModelType
  ) {}
}
