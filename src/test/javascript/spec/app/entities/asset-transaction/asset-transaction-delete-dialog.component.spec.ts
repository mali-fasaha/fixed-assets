/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { FixedAssetsTestModule } from '../../../test.module';
import { AssetTransactionDeleteDialogComponent } from 'app/entities/asset-transaction/asset-transaction-delete-dialog.component';
import { AssetTransactionService } from 'app/entities/asset-transaction/asset-transaction.service';

describe('Component Tests', () => {
  describe('AssetTransaction Management Delete Component', () => {
    let comp: AssetTransactionDeleteDialogComponent;
    let fixture: ComponentFixture<AssetTransactionDeleteDialogComponent>;
    let service: AssetTransactionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [AssetTransactionDeleteDialogComponent]
      })
        .overrideTemplate(AssetTransactionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AssetTransactionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AssetTransactionService);
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
