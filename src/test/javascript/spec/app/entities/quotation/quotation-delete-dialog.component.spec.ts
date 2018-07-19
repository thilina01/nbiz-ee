/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NBizEeTestModule } from '../../../test.module';
import { QuotationDeleteDialogComponent } from 'app/entities/quotation/quotation-delete-dialog.component';
import { QuotationService } from 'app/entities/quotation/quotation.service';

describe('Component Tests', () => {
    describe('Quotation Management Delete Component', () => {
        let comp: QuotationDeleteDialogComponent;
        let fixture: ComponentFixture<QuotationDeleteDialogComponent>;
        let service: QuotationService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NBizEeTestModule],
                declarations: [QuotationDeleteDialogComponent]
            })
                .overrideTemplate(QuotationDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(QuotationDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QuotationService);
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
