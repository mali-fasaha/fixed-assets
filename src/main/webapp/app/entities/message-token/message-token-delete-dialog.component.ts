import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMessageToken } from 'app/shared/model/message-token.model';
import { MessageTokenService } from './message-token.service';

@Component({
  selector: 'gha-message-token-delete-dialog',
  templateUrl: './message-token-delete-dialog.component.html'
})
export class MessageTokenDeleteDialogComponent {
  messageToken: IMessageToken;

  constructor(
    protected messageTokenService: MessageTokenService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.messageTokenService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'messageTokenListModification',
        content: 'Deleted an messageToken'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'gha-message-token-delete-popup',
  template: ''
})
export class MessageTokenDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ messageToken }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(MessageTokenDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.messageToken = messageToken;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/message-token', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/message-token', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
