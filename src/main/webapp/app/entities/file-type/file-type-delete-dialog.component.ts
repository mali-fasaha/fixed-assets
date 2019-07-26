import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFileType } from 'app/shared/model/file-type.model';
import { FileTypeService } from './file-type.service';

@Component({
  selector: 'gha-file-type-delete-dialog',
  templateUrl: './file-type-delete-dialog.component.html'
})
export class FileTypeDeleteDialogComponent {
  fileType: IFileType;

  constructor(protected fileTypeService: FileTypeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.fileTypeService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'fileTypeListModification',
        content: 'Deleted an fileType'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'gha-file-type-delete-popup',
  template: ''
})
export class FileTypeDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ fileType }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(FileTypeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.fileType = fileType;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/file-type', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/file-type', { outlets: { popup: null } }]);
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
