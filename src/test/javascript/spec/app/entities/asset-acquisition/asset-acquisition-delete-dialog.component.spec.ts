/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { FixedAssetsTestModule } from '../../../test.module';
import { AssetAcquisitionDeleteDialogComponent } from 'app/entities/asset-acquisition/asset-acquisition-delete-dialog.component';
import { AssetAcquisitionService } from 'app/entities/asset-acquisition/asset-acquisition.service';

describe('Component Tests', () => {
  describe('AssetAcquisition Management Delete Component', () => {
    let comp: AssetAcquisitionDeleteDialogComponent;
    let fixture: ComponentFixture<AssetAcquisitionDeleteDialogComponent>;
    let service: AssetAcquisitionService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [AssetAcquisitionDeleteDialogComponent]
      })
        .overrideTemplate(AssetAcquisitionDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AssetAcquisitionDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AssetAcquisitionService);
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
