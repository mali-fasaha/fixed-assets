import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICapitalWorkInProgress } from 'app/shared/model/capital-work-in-progress.model';
import { CapitalWorkInProgressService } from './capital-work-in-progress.service';

@Component({
  selector: 'gha-capital-work-in-progress-delete-dialog',
  templateUrl: './capital-work-in-progress-delete-dialog.component.html'
})
export class CapitalWorkInProgressDeleteDialogComponent {
  capitalWorkInProgress: ICapitalWorkInProgress;

  constructor(
    protected capitalWorkInProgressService: CapitalWorkInProgressService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.capitalWorkInProgressService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'capitalWorkInProgressListModification',
        content: 'Deleted an capitalWorkInProgress'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'gha-capital-work-in-progress-delete-popup',
  template: ''
})
export class CapitalWorkInProgressDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ capitalWorkInProgress }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(CapitalWorkInProgressDeleteDialogComponent as Component, {
          size: 'lg',
          backdrop: 'static'
        });
        this.ngbModalRef.componentInstance.capitalWorkInProgress = capitalWorkInProgress;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/capital-work-in-progress', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/capital-work-in-progress', { outlets: { popup: null } }]);
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
