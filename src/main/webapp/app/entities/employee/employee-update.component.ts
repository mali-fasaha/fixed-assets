import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';
import { IEmployee, Employee } from 'app/shared/model/employee.model';
import { EmployeeService } from './employee.service';

@Component({
  selector: 'gha-employee-update',
  templateUrl: './employee-update.component.html'
})
export class EmployeeUpdateComponent implements OnInit {
  employee: IEmployee;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    employeeName: [null, [Validators.required]],
    serviceOutletCode: [],
    employeeRole: [],
    employeeStaffCode: [],
    employeeSignature: [],
    employeeSignatureContentType: [],
    employeeEmail: []
  });

  constructor(
    protected dataUtils: JhiDataUtils,
    protected jhiAlertService: JhiAlertService,
    protected employeeService: EmployeeService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ employee }) => {
      this.updateForm(employee);
      this.employee = employee;
    });
  }

  updateForm(employee: IEmployee) {
    this.editForm.patchValue({
      id: employee.id,
      employeeName: employee.employeeName,
      serviceOutletCode: employee.serviceOutletCode,
      employeeRole: employee.employeeRole,
      employeeStaffCode: employee.employeeStaffCode,
      employeeSignature: employee.employeeSignature,
      employeeSignatureContentType: employee.employeeSignatureContentType,
      employeeEmail: employee.employeeEmail
    });
  }

  byteSize(field) {
    return this.dataUtils.byteSize(field);
  }

  openFile(contentType, field) {
    return this.dataUtils.openFile(contentType, field);
  }

  setFileData(event, field: string, isImage) {
    return new Promise((resolve, reject) => {
      if (event && event.target && event.target.files && event.target.files[0]) {
        const file = event.target.files[0];
        if (isImage && !/^image\//.test(file.type)) {
          reject(`File was expected to be an image but was found to be ${file.type}`);
        } else {
          const filedContentType: string = field + 'ContentType';
          this.dataUtils.toBase64(file, base64Data => {
            this.editForm.patchValue({
              [field]: base64Data,
              [filedContentType]: file.type
            });
          });
        }
      } else {
        reject(`Base64 data was not set as file could not be extracted from passed parameter: ${event}`);
      }
    }).then(
      () => console.log('blob added'), // sucess
      this.onError
    );
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const employee = this.createFromForm();
    if (employee.id !== undefined) {
      this.subscribeToSaveResponse(this.employeeService.update(employee));
    } else {
      this.subscribeToSaveResponse(this.employeeService.create(employee));
    }
  }

  private createFromForm(): IEmployee {
    const entity = {
      ...new Employee(),
      id: this.editForm.get(['id']).value,
      employeeName: this.editForm.get(['employeeName']).value,
      serviceOutletCode: this.editForm.get(['serviceOutletCode']).value,
      employeeRole: this.editForm.get(['employeeRole']).value,
      employeeStaffCode: this.editForm.get(['employeeStaffCode']).value,
      employeeSignatureContentType: this.editForm.get(['employeeSignatureContentType']).value,
      employeeSignature: this.editForm.get(['employeeSignature']).value,
      employeeEmail: this.editForm.get(['employeeEmail']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployee>>) {
    result.subscribe((res: HttpResponse<IEmployee>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }
}
