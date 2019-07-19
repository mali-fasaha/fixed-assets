/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { FixedAssetsTestModule } from '../../../test.module';
import { FixedAssetItemDeleteDialogComponent } from 'app/entities/fixed-asset-item/fixed-asset-item-delete-dialog.component';
import { FixedAssetItemService } from 'app/entities/fixed-asset-item/fixed-asset-item.service';

describe('Component Tests', () => {
  describe('FixedAssetItem Management Delete Component', () => {
    let comp: FixedAssetItemDeleteDialogComponent;
    let fixture: ComponentFixture<FixedAssetItemDeleteDialogComponent>;
    let service: FixedAssetItemService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [FixedAssetItemDeleteDialogComponent]
      })
        .overrideTemplate(FixedAssetItemDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(FixedAssetItemDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(FixedAssetItemService);
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
