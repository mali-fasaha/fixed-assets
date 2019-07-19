/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { FixedAssetsTestModule } from '../../../test.module';
import { ServiceOutletDeleteDialogComponent } from 'app/entities/service-outlet/service-outlet-delete-dialog.component';
import { ServiceOutletService } from 'app/entities/service-outlet/service-outlet.service';

describe('Component Tests', () => {
  describe('ServiceOutlet Management Delete Component', () => {
    let comp: ServiceOutletDeleteDialogComponent;
    let fixture: ComponentFixture<ServiceOutletDeleteDialogComponent>;
    let service: ServiceOutletService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [ServiceOutletDeleteDialogComponent]
      })
        .overrideTemplate(ServiceOutletDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ServiceOutletDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ServiceOutletService);
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
