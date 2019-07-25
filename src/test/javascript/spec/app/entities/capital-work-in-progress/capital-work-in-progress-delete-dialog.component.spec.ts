/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { FixedAssetsTestModule } from '../../../test.module';
import { CapitalWorkInProgressDeleteDialogComponent } from 'app/entities/capital-work-in-progress/capital-work-in-progress-delete-dialog.component';
import { CapitalWorkInProgressService } from 'app/entities/capital-work-in-progress/capital-work-in-progress.service';

describe('Component Tests', () => {
  describe('CapitalWorkInProgress Management Delete Component', () => {
    let comp: CapitalWorkInProgressDeleteDialogComponent;
    let fixture: ComponentFixture<CapitalWorkInProgressDeleteDialogComponent>;
    let service: CapitalWorkInProgressService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [FixedAssetsTestModule],
        declarations: [CapitalWorkInProgressDeleteDialogComponent]
      })
        .overrideTemplate(CapitalWorkInProgressDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(CapitalWorkInProgressDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CapitalWorkInProgressService);
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
