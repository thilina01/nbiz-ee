import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQuotationItem } from 'app/shared/model/quotation-item.model';
import { QuotationItemService } from './quotation-item.service';

@Component({
    selector: 'jhi-quotation-item-delete-dialog',
    templateUrl: './quotation-item-delete-dialog.component.html'
})
export class QuotationItemDeleteDialogComponent {
    quotationItem: IQuotationItem;

    constructor(
        private quotationItemService: QuotationItemService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.quotationItemService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'quotationItemListModification',
                content: 'Deleted an quotationItem'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-quotation-item-delete-popup',
    template: ''
})
export class QuotationItemDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ quotationItem }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(QuotationItemDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.quotationItem = quotationItem;
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
