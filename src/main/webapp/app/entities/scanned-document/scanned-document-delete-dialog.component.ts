import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IScannedDocument } from 'app/shared/model/scanned-document.model';
import { ScannedDocumentService } from './scanned-document.service';

@Component({
  selector: 'jhi-scanned-document-delete-dialog',
  templateUrl: './scanned-document-delete-dialog.component.html'
})
export class ScannedDocumentDeleteDialogComponent {
  scannedDocument: IScannedDocument;

  constructor(
    protected scannedDocumentService: ScannedDocumentService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.scannedDocumentService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'scannedDocumentListModification',
        content: 'Deleted an scannedDocument'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-scanned-document-delete-popup',
  template: ''
})
export class ScannedDocumentDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ scannedDocument }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ScannedDocumentDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.scannedDocument = scannedDocument;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/scanned-document', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/scanned-document', { outlets: { popup: null } }]);
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
