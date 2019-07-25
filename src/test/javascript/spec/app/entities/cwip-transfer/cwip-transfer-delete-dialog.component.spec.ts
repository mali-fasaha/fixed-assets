/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { FixedAssetsTestModule } from '../../../test.module';
import { CwipTransferDeleteDialogComponent } from 'app/entities/cwip-transfer/cwip-transfer-delete-dialog.component';
import { CwipTransferService } from 'app/entities/cwip-transfer/cwip-transfer.service';

describe('Component Tests', () => {
  describe('CwipTransfer Management Delete Component', () => {
    let comp: CwipTransferDeleteDialogComponent;
    let fixture: ComponentFixture<CwipTransferDeleteDialogComponent>;
    let service: CwipTransferService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [CwipTransferDeleteDialogComponent]
      })
        .overrideTemplate(CwipTransferDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CwipTransferDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CwipTransferService);
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
