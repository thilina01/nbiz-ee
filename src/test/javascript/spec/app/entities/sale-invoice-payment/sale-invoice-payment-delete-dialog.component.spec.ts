/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { NBizEeTestModule } from '../../../test.module';
import { SaleInvoicePaymentDeleteDialogComponent } from 'app/entities/sale-invoice-payment/sale-invoice-payment-delete-dialog.component';
import { SaleInvoicePaymentService } from 'app/entities/sale-invoice-payment/sale-invoice-payment.service';

describe('Component Tests', () => {
    describe('SaleInvoicePayment Management Delete Component', () => {
        let comp: SaleInvoicePaymentDeleteDialogComponent;
        let fixture: ComponentFixture<SaleInvoicePaymentDeleteDialogComponent>;
        let service: SaleInvoicePaymentService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [NBizEeTestModule],
                declarations: [SaleInvoicePaymentDeleteDialogComponent]
            })
                .overrideTemplate(SaleInvoicePaymentDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SaleInvoicePaymentDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SaleInvoicePaymentService);
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
