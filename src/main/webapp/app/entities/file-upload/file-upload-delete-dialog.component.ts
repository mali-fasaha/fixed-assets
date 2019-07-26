import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFileUpload } from 'app/shared/model/file-upload.model';
import { FileUploadService } from './file-upload.service';

@Component({
  selector: 'gha-file-upload-delete-dialog',
  templateUrl: './file-upload-delete-dialog.component.html'
})
export class FileUploadDeleteDialogComponent {
  fileUpload: IFileUpload;

  constructor(
    protected fileUploadService: FileUploadService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.fileUploadService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'fileUploadListModification',
        content: 'Deleted an fileUpload'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'gha-file-upload-delete-popup',
  template: ''
})
export class FileUploadDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fileUpload }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FileUploadDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.fileUpload = fileUpload;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/file-upload', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/file-upload', { outlets: { popup: null } }]);
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
