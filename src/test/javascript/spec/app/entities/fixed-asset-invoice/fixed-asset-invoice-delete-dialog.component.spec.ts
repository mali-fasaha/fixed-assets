/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { FixedAssetsTestModule } from '../../../test.module';
import { FixedAssetInvoiceDeleteDialogComponent } from 'app/entities/fixed-asset-invoice/fixed-asset-invoice-delete-dialog.component';
import { FixedAssetInvoiceService } from 'app/entities/fixed-asset-invoice/fixed-asset-invoice.service';

describe('Component Tests', () => {
  describe('FixedAssetInvoice Management Delete Component', () => {
    let comp: FixedAssetInvoiceDeleteDialogComponent;
    let fixture: ComponentFixture<FixedAssetInvoiceDeleteDialogComponent>;
    let service: FixedAssetInvoiceService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [FixedAssetInvoiceDeleteDialogComponent]
      })
        .overrideTemplate(FixedAssetInvoiceDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FixedAssetInvoiceDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FixedAssetInvoiceService);
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
