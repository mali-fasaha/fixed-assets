export interface IEmployee {
  id?: number;
  employeeName?: string;
  serviceOutletCode?: string;
  employeeRole?: string;
  employeeStaffCode?: string;
  employeeSignatureContentType?: string;
  employeeSignature?: any;
  employeeEmail?: string;
}

export class Employee implements IEmployee {
  constructor(
    public id?: number,
    public employeeName?: string,
    public serviceOutletCode?: string,
    public employeeRole?: string,
    public employeeStaffCode?: string,
    public employeeSignatureContentType?: string,
    public employeeSignature?: any,
    public employeeEmail?: string
  ) {}
}
