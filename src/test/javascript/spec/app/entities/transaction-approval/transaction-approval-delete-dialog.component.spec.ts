/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { FixedAssetsTestModule } from '../../../test.module';
import { TransactionApprovalDeleteDialogComponent } from 'app/entities/transaction-approval/transaction-approval-delete-dialog.component';
import { TransactionApprovalService } from 'app/entities/transaction-approval/transaction-approval.service';

describe('Component Tests', () => {
  describe('TransactionApproval Management Delete Component', () => {
    let comp: TransactionApprovalDeleteDialogComponent;
    let fixture: ComponentFixture<TransactionApprovalDeleteDialogComponent>;
    let service: TransactionApprovalService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [TransactionApprovalDeleteDialogComponent]
      })
        .overrideTemplate(TransactionApprovalDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TransactionApprovalDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TransactionApprovalService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
