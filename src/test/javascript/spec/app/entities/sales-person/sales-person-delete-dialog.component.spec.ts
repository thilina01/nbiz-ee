/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NBizEeTestModule } from '../../../test.module';
import { SalesPersonDeleteDialogComponent } from 'app/entities/sales-person/sales-person-delete-dialog.component';
import { SalesPersonService } from 'app/entities/sales-person/sales-person.service';

describe('Component Tests', () => {
    describe('SalesPerson Management Delete Component', () => {
        let comp: SalesPersonDeleteDialogComponent;
        let fixture: ComponentFixture<SalesPersonDeleteDialogComponent>;
        let service: SalesPersonService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NBizEeTestModule],
                declarations: [SalesPersonDeleteDialogComponent]
            })
                .overrideTemplate(SalesPersonDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SalesPersonDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SalesPersonService);
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
