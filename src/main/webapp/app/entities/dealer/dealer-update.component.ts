import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IDealer, Dealer } from 'app/shared/model/dealer.model';
import { DealerService } from './dealer.service';

@Component({
  selector: 'gha-dealer-update',
  templateUrl: './dealer-update.component.html'
})
export class DealerUpdateComponent implements OnInit {
  dealer: IDealer;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    title: [null, [Validators.required]],
    dealerName: [null, [Validators.required]],
    dealerAddress: [],
    dealerPhoneNumber: [],
    dealerEmail: [],
    bankName: [],
    bankAccountNumber: [],
    bankBranch: [],
    bankSwiftCode: [],
    bankPhysicalAddress: [],
    domicile: [],
    taxAuthorityRef: []
  });

  constructor(protected dealerService: DealerService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ dealer }) => {
      this.updateForm(dealer);
      this.dealer = dealer;
    });
  }

  updateForm(dealer: IDealer) {
    this.editForm.patchValue({
      id: dealer.id,
      title: dealer.title,
      dealerName: dealer.dealerName,
      dealerAddress: dealer.dealerAddress,
      dealerPhoneNumber: dealer.dealerPhoneNumber,
      dealerEmail: dealer.dealerEmail,
      bankName: dealer.bankName,
      bankAccountNumber: dealer.bankAccountNumber,
      bankBranch: dealer.bankBranch,
      bankSwiftCode: dealer.bankSwiftCode,
      bankPhysicalAddress: dealer.bankPhysicalAddress,
      domicile: dealer.domicile,
      taxAuthorityRef: dealer.taxAuthorityRef
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const dealer = this.createFromForm();
    if (dealer.id !== undefined) {
      this.subscribeToSaveResponse(this.dealerService.update(dealer));
    } else {
      this.subscribeToSaveResponse(this.dealerService.create(dealer));
    }
  }

  private createFromForm(): IDealer {
    const entity = {
      ...new Dealer(),
      id: this.editForm.get(['id']).value,
      title: this.editForm.get(['title']).value,
      dealerName: this.editForm.get(['dealerName']).value,
      dealerAddress: this.editForm.get(['dealerAddress']).value,
      dealerPhoneNumber: this.editForm.get(['dealerPhoneNumber']).value,
      dealerEmail: this.editForm.get(['dealerEmail']).value,
      bankName: this.editForm.get(['bankName']).value,
      bankAccountNumber: this.editForm.get(['bankAccountNumber']).value,
      bankBranch: this.editForm.get(['bankBranch']).value,
      bankSwiftCode: this.editForm.get(['bankSwiftCode']).value,
      bankPhysicalAddress: this.editForm.get(['bankPhysicalAddress']).value,
      domicile: this.editForm.get(['domicile']).value,
      taxAuthorityRef: this.editForm.get(['taxAuthorityRef']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDealer>>) {
    result.subscribe((res: HttpResponse<IDealer>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
