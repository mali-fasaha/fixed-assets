import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { IMessageToken, MessageToken } from 'app/shared/model/message-token.model';
import { MessageTokenService } from './message-token.service';

@Component({
  selector: 'gha-message-token-update',
  templateUrl: './message-token-update.component.html'
})
export class MessageTokenUpdateComponent implements OnInit {
  messageToken: IMessageToken;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    description: [],
    timeSent: [null, [Validators.required]],
    tokenValue: [null, [Validators.required]],
    received: []
  });

  constructor(protected messageTokenService: MessageTokenService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ messageToken }) => {
      this.updateForm(messageToken);
      this.messageToken = messageToken;
    });
  }

  updateForm(messageToken: IMessageToken) {
    this.editForm.patchValue({
      id: messageToken.id,
      description: messageToken.description,
      timeSent: messageToken.timeSent,
      tokenValue: messageToken.tokenValue,
      received: messageToken.received
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const messageToken = this.createFromForm();
    if (messageToken.id !== undefined) {
      this.subscribeToSaveResponse(this.messageTokenService.update(messageToken));
    } else {
      this.subscribeToSaveResponse(this.messageTokenService.create(messageToken));
    }
  }

  private createFromForm(): IMessageToken {
    const entity = {
      ...new MessageToken(),
      id: this.editForm.get(['id']).value,
      description: this.editForm.get(['description']).value,
      timeSent: this.editForm.get(['timeSent']).value,
      tokenValue: this.editForm.get(['tokenValue']).value,
      received: this.editForm.get(['received']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IMessageToken>>) {
    result.subscribe((res: HttpResponse<IMessageToken>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
