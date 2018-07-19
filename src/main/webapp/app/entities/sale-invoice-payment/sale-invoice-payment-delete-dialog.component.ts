import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISaleInvoicePayment } from 'app/shared/model/sale-invoice-payment.model';
import { SaleInvoicePaymentService } from './sale-invoice-payment.service';

@Component({
    selector: 'jhi-sale-invoice-payment-delete-dialog',
    templateUrl: './sale-invoice-payment-delete-dialog.component.html'
})
export class SaleInvoicePaymentDeleteDialogComponent {
    saleInvoicePayment: ISaleInvoicePayment;

    constructor(
        private saleInvoicePaymentService: SaleInvoicePaymentService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.saleInvoicePaymentService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'saleInvoicePaymentListModification',
                content: 'Deleted an saleInvoicePayment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sale-invoice-payment-delete-popup',
    template: ''
})
export class SaleInvoicePaymentDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ saleInvoicePayment }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(SaleInvoicePaymentDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.saleInvoicePayment = saleInvoicePayment;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
