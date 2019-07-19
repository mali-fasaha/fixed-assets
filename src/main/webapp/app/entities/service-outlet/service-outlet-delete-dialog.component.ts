import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServiceOutlet } from 'app/shared/model/service-outlet.model';
import { ServiceOutletService } from './service-outlet.service';

@Component({
  selector: 'gha-service-outlet-delete-dialog',
  templateUrl: './service-outlet-delete-dialog.component.html'
})
export class ServiceOutletDeleteDialogComponent {
  serviceOutlet: IServiceOutlet;

  constructor(
    protected serviceOutletService: ServiceOutletService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.serviceOutletService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'serviceOutletListModification',
        content: 'Deleted an serviceOutlet'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'gha-service-outlet-delete-popup',
  template: ''
})
export class ServiceOutletDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ serviceOutlet }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(ServiceOutletDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.serviceOutlet = serviceOutlet;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/service-outlet', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/service-outlet', { outlets: { popup: null } }]);
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
