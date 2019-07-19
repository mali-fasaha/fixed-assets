import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IServiceOutlet, ServiceOutlet } from 'app/shared/model/service-outlet.model';
import { ServiceOutletService } from './service-outlet.service';

@Component({
  selector: 'gha-service-outlet-update',
  templateUrl: './service-outlet-update.component.html'
})
export class ServiceOutletUpdateComponent implements OnInit {
  serviceOutlet: IServiceOutlet;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    serviceOutletCode: [null, [Validators.required]],
    serviceOutletDesignation: [null, [Validators.required]],
    description: [],
    location: []
  });

  constructor(protected serviceOutletService: ServiceOutletService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ serviceOutlet }) => {
      this.updateForm(serviceOutlet);
      this.serviceOutlet = serviceOutlet;
    });
  }

  updateForm(serviceOutlet: IServiceOutlet) {
    this.editForm.patchValue({
      id: serviceOutlet.id,
      serviceOutletCode: serviceOutlet.serviceOutletCode,
      serviceOutletDesignation: serviceOutlet.serviceOutletDesignation,
      description: serviceOutlet.description,
      location: serviceOutlet.location
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const serviceOutlet = this.createFromForm();
    if (serviceOutlet.id !== undefined) {
      this.subscribeToSaveResponse(this.serviceOutletService.update(serviceOutlet));
    } else {
      this.subscribeToSaveResponse(this.serviceOutletService.create(serviceOutlet));
    }
  }

  private createFromForm(): IServiceOutlet {
    const entity = {
      ...new ServiceOutlet(),
      id: this.editForm.get(['id']).value,
      serviceOutletCode: this.editForm.get(['serviceOutletCode']).value,
      serviceOutletDesignation: this.editForm.get(['serviceOutletDesignation']).value,
      description: this.editForm.get(['description']).value,
      location: this.editForm.get(['location']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IServiceOutlet>>) {
    result.subscribe((res: HttpResponse<IServiceOutlet>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
